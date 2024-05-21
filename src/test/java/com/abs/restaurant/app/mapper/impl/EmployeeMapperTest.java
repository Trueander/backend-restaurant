package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.entity.dto.employee.EmployeeDto;
import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.entity.dto.RoleDto;
import com.abs.restaurant.app.util.EntityMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeMapperTest {

    private EmployeeMapper employeeMapper;

    @Mock
    private RoleMapper roleMapper;

    @BeforeEach
    public void init() {
        employeeMapper = new EmployeeMapper(roleMapper);
    }

    @Test
    public void mapFromEmployeeToEmployeeDtoTest() throws IOException {
        Mockito.when(roleMapper.mapRoleToRoleDto(any(Role.class))).thenReturn(new RoleDto(1,"ADMIN"));

        Employee employee = EntityMock.getInstance().createEmployee();
        EmployeeDto employeeDto = employeeMapper.mapEmployeeToEmployeeDto(employee);
        assertNotNull(employeeDto);
        assertNotNull(employeeDto.id());
        assertNotNull(employeeDto.dni());
        assertNotNull(employeeDto.email());
        assertNotNull(employeeDto.firstname());
        assertNotNull(employeeDto.lastname());
        assertNotNull(employeeDto.phoneNumber());
        assertNotNull(employeeDto.roles());
        assertNotNull(employeeDto.roles().get(0));
        assertNotNull(employeeDto.roles().get(0).id());
        assertNotNull(employeeDto.roles().get(0).name());
        assertNotNull(employeeDto.roles().get(1));
        assertNotNull(employeeDto.roles().get(1).id());
        assertNotNull(employeeDto.roles().get(1).name());

        assertEquals(employeeDto.id(), employee.getId());
        assertEquals(employeeDto.dni(), employee.getUser().getDni());
        assertEquals(employeeDto.email(), employee.getUser().getEmail());
        assertEquals(employeeDto.firstname(), employee.getUser().getFirstname());
        assertEquals(employeeDto.lastname(), employee.getUser().getLastname());
        assertEquals(employeeDto.phoneNumber(), employee.getUser().getPhoneNumber());
        assertEquals(employeeDto.roles().size(), employee.getUser().getRoles().size());

        verify(roleMapper, times(2)).mapRoleToRoleDto(any(Role.class));
    }
}