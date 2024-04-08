package com.abs.restaurant.app.entity.dto.employee;

public record EmployeeDto(
        Long id,
        String firstname,
        String lastname,
        String dni,
        String email,
        String phoneNumber) {
}