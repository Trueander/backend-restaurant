package com.abs.restaurant.app.security.auth;

import com.abs.restaurant.app.exceptions.ConflictException;
import com.abs.restaurant.app.exceptions.ResourceNotFoundException;
import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.entity.User;
import com.abs.restaurant.app.security.repository.RoleRepository;
import com.abs.restaurant.app.security.repository.UserRepository;
import com.abs.restaurant.app.security.service.impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest request) {
        checkIfFieldsAreTaken(request);
        List<Role> rolesByIds = roleRepository.findRolesByIds(request.getRoleIds());
        verifyRoles(rolesByIds, request.getRoleIds());

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .dni(request.getDni())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getDni()))
                .roles(new HashSet<>(rolesByIds))
                .build();

        return userRepository.save(user);
    }

    public void updateUser(Long userId, RegisterRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID: "+userId+" no existe"));

        if(!request.getEmail().equals(user.getEmail()) || !request.getDni().equals(user.getDni())) {
            checkIfFieldsAreTaken(request);
        }

        List<Role> rolesByIds = roleRepository.findRolesByIds(request.getRoleIds());
        verifyRoles(rolesByIds, request.getRoleIds());

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDni(request.getDni());
        user.setEmail(request.getEmail());
        user.setRoles(new HashSet<>(rolesByIds));

        userRepository.save(user);
    }

    private void verifyRoles(List<Role> roles, Set<Integer> rolesIds) {
        Set<Integer> existingRoleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        rolesIds.forEach(roleId -> {
            if(!existingRoleIds.contains(roleId)) {
                throw new ResourceNotFoundException("El rol con el ID: "+roleId+ " no existe");
            }
        });
    }

    private void checkIfFieldsAreTaken(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("El email ya está registrado: " + request.getEmail());
        }

        if(userRepository.existsByDni(request.getDni())) {
            throw new ConflictException("El dni ya está registrado: " + request.getDni());
        }
    }

    public AuthenticationResponse login(LoginRequest request) {
        //validate if the user is ok, then continue
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return new AuthenticationResponse(jwtService.generateToken(user));
    }
}
