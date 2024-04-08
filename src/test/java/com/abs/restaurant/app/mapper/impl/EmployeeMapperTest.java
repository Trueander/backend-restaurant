package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.entity.dto.employee.EmployeeDto;
import com.abs.restaurant.app.util.EntityMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeMapperTest {

    private EmployeeMapper employeeMapper;

    @BeforeEach
    public void init() {
        employeeMapper = new EmployeeMapper();
    }

    @Test
    public void mapFromEmployeeToEmployeeDtoTest() throws IOException {
        Employee employee = EntityMock.getInstance().createEmployee();
        EmployeeDto employeeDto = employeeMapper.mapEmployeeToEmployeeDto(employee);
        assertNotNull(employeeDto);
        assertNotNull(employeeDto.id());
        assertNotNull(employeeDto.dni());
        assertNotNull(employeeDto.email());
        assertNotNull(employeeDto.firstname());
        assertNotNull(employeeDto.lastname());
        assertNotNull(employeeDto.phoneNumber());

        assertEquals(employeeDto.id(), employee.getId());
        assertEquals(employeeDto.dni(), employee.getUser().getDni());
        assertEquals(employeeDto.email(), employee.getUser().getEmail());
        assertEquals(employeeDto.firstname(), employee.getUser().getFirstname());
        assertEquals(employeeDto.lastname(), employee.getUser().getLastname());
        assertEquals(employeeDto.phoneNumber(), employee.getUser().getPhoneNumber());
    }
}