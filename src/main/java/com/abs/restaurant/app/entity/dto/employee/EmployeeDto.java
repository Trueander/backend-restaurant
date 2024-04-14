package com.abs.restaurant.app.entity.dto.employee;

import com.abs.restaurant.app.security.entity.dto.RoleDto;

import java.util.List;

public record EmployeeDto(
        Long id,
        String firstname,
        String lastname,
        String dni,
        String email,
        String phoneNumber,
        List<RoleDto> roles) {
}