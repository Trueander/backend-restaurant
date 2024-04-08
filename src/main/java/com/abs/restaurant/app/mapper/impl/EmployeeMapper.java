package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.entity.dto.employee.EmployeeDto;
import com.abs.restaurant.app.mapper.IEmployeeMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EmployeeMapper implements IEmployeeMapper {

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
                employee.getUser().getPhoneNumber()
        );
    }
}