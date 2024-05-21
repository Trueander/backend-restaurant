package com.abs.restaurant.app.security.service.impl;

import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void getRolesTest() {
        when(roleRepository.findAll()).thenReturn(List.of(new Role(1, "ADMIN")));

        List<Role> roles = roleService.getRoles();

        assertNotNull(roles);
        assertNotNull(roles.get(0));
        assertNotNull(roles.get(0).getId());
        assertNotNull(roles.get(0).getName());

        assertEquals(roles.get(0).getId(), 1);
        assertEquals(roles.get(0).getName(), "ADMIN");

        verify(roleRepository).findAll();
    }
}