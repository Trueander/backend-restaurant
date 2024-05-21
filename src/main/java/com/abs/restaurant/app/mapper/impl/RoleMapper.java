package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.mapper.IRoleMapper;
import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.entity.dto.RoleDto;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements IRoleMapper {

    @Override
    public RoleDto mapRoleToRoleDto(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }
}