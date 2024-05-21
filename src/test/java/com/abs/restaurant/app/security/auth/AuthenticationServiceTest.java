package com.abs.restaurant.app.security.auth;

import com.abs.restaurant.app.exceptions.ConflictException;
import com.abs.restaurant.app.exceptions.ResourceNotFoundException;
import com.abs.restaurant.app.security.entity.User;
import com.abs.restaurant.app.security.repository.RoleRepository;
import com.abs.restaurant.app.security.repository.UserRepository;
import com.abs.restaurant.app.security.service.impl.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static com.abs.restaurant.app.util.EntityMock.getInstance;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(userRepository,roleRepository,passwordEncoder,jwtService, authenticationManager);
    }

    @Test
    void registerFullTest() throws IOException {
        String encriptedPassword = "$12$SB4gFOHLZ5.FE2uojL1mmethm5E/C066p9P38DIFGyCVB.rlKuQja";
        when(userRepository.existsByDni(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(getInstance().getUser());
        when(roleRepository.findRolesByIds(anySet())).thenReturn(getInstance().getRoles());
        when(passwordEncoder.encode(anyString())).thenReturn(encriptedPassword);

        RegisterRequest registerRequest = getInstance().requestUser();
        User user = authenticationService.register(registerRequest);
        assertNotNull(user);
        assertNotNull(user.getFirstname());
        assertNotNull(user.getLastname());
        assertNotNull(user.getDni());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getRoles());

        assertEquals(2, user.getRoles().size());
        assertEquals(encriptedPassword, user.getPassword());

        verify(roleRepository).findRolesByIds(anySet());
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).existsByDni(anyString());
    }

    @Test
    void registerEmailAlreadyExistTest() throws IOException {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        RegisterRequest registerRequest = getInstance().requestUser();

        ConflictException conflictException = assertThrows(ConflictException.class, () ->
                authenticationService.register(registerRequest));

        assertEquals("El email ya está registrado: "+registerRequest.getEmail(),conflictException.getMessage());

        verify(userRepository).existsByEmail(anyString());
        verify(roleRepository, never()).findRolesByIds(anySet());
    }

    @Test
    void registerDniAlreadyExistTest() throws IOException {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByDni(anyString())).thenReturn(true);

        RegisterRequest registerRequest = getInstance().requestUser();

        ConflictException conflictException = assertThrows(ConflictException.class, () ->
                authenticationService.register(registerRequest));

        assertEquals("El dni ya está registrado: "+registerRequest.getDni(),conflictException.getMessage());

        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).existsByDni(anyString());
        verify(roleRepository, never()).findRolesByIds(anySet());
    }

    @Test
    void registerRoleNotFoundTest() throws IOException {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByDni(anyString())).thenReturn(false);
        when(roleRepository.findRolesByIds(anySet())).thenReturn(getInstance().getRoles());

        RegisterRequest registerRequest = getInstance().requestUser();
        int roleIdNotFound = 3;
        registerRequest.getRoleIds().add(roleIdNotFound);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                authenticationService.register(registerRequest));

        assertEquals("El rol con el ID: 3 no existe",exception.getMessage());

        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).existsByDni(anyString());
        verify(roleRepository).findRolesByIds(anySet());
    }

    @Test
    void updateUserFullTest() throws IOException {
        when(userRepository.existsByDni(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(getInstance().getUser()));
        when(roleRepository.findRolesByIds(anySet())).thenReturn(getInstance().getRoles());

        RegisterRequest registerRequest = getInstance().requestUser();
        registerRequest.setDni("77777777");
        registerRequest.setEmail("ax@gmail.com");
        authenticationService.updateUser(1L, registerRequest);

        verify(roleRepository).findRolesByIds(anySet());
        verify(userRepository).save(any(User.class));
        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).existsByDni(anyString());
        verify(userRepository).findById(anyLong());
    }

    @Test
    void updateUserNotFoundTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                authenticationService.updateUser(2L, new RegisterRequest()));

        verify(userRepository).findById(2L);
    }

    @Test
    void loginFullTest() throws IOException {
        String fakeToken = "FAKE_TOKEN";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(getInstance().getUser()));
        when(jwtService.generateToken(any(User.class))).thenReturn(fakeToken);

        LoginRequest loginRequest = new LoginRequest("ander@gmail.com", "70267159");
        AuthenticationResponse loginResponse = authenticationService.login(loginRequest);
        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getToken());

        assertEquals(fakeToken, loginResponse.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(anyString());
        verify(jwtService).generateToken(any(User.class));
    }

    @Test
    void loginUserNotFoundTest() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        LoginRequest loginRequest = new LoginRequest("ander@gmail.com", "70267159");
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                authenticationService.login(loginRequest));

        assertEquals(exception.getMessage(), "El usuario ander@gmail.com no existe");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(anyString());
        verify(jwtService, never()).generateToken(any(User.class));
    }
}