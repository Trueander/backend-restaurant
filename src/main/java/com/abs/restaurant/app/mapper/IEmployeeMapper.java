package com.abs.restaurant.app.mapper;

import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.entity.dto.employee.EmployeeDto;

public interface IEmployeeMapper {
    EmployeeDto mapEmployeeToEmployeeDto(Employee employee);
}