package com.abs.restaurant.app.security.auth;

import com.abs.restaurant.app.exceptions.ConflictException;
import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.entity.User;
import com.abs.restaurant.app.security.repository.RoleRepository;
import com.abs.restaurant.app.security.repository.UserRepository;
import com.abs.restaurant.app.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> userFound = userRepository.findByEmailOrDni(request.getEmail(), request.getDni());

        userFound.ifPresent(user -> {
            if(user.getDni().equals(request.getDni())) {
                throw new ConflictException("El dni ya está registrado: " + user.getDni());
            } else if(user.getEmail().equals(request.getEmail())) {
                throw new ConflictException("El email ya está registrado: " + user.getEmail());
            }
        });

        List<Role> rolesByIds = roleRepository.findRolesByIds(request.getRoleIds());

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .dni(request.getDni())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(new HashSet<>(rolesByIds))
                .build();

        userRepository.save(user);
        return new AuthenticationResponse(jwtService.generateToken(user));
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
