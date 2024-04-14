package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.entity.dto.employee.EmployeeDto;
import com.abs.restaurant.app.mapper.IEmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper implements IEmployeeMapper {

    private final RoleMapper roleMapper;

    @Override
    public EmployeeDto mapEmployeeToEmployeeDto(Employee employee) {
        if(Objects.isNull(employee) || Objects.isNull(employee.getUser())) {
            return null;
        }

        return new EmployeeDto(
                employee.getId(),
                employee.getUser().getFirstname(),
                employee.getUser().getLastname(),
                employee.getUser().getDni(),
                employee.getUser().getEmail(),
                employee.getUser().getPhoneNumber(),
                employee.getUser().getRoles()
                        .stream()
                        .map(roleMapper::mapRoleToRoleDto)
                        .collect(Collectors.toList())
        );
    }
}