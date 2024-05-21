package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.entity.dto.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleMapperTest {
    private RoleMapper roleMapper;

    @BeforeEach
    public void init() {
        roleMapper = new RoleMapper();
    }

    @Test
    public void mapFromRoleToRoleDtoTest() {
        RoleDto role = roleMapper.mapRoleToRoleDto(new Role(1, "ADMIN"));

        assertNotNull(role);
        assertNotNull(role.id());
        assertNotNull(role.name());
    }
}