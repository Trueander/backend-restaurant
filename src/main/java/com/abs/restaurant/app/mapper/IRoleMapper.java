package com.abs.restaurant.app.mapper;

import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.entity.dto.RoleDto;

public interface IRoleMapper {
    RoleDto mapRoleToRoleDto(Role role);
}