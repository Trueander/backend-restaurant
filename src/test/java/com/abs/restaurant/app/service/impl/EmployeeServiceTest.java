package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.repository.EmployeeRepository;
import com.abs.restaurant.app.security.auth.AuthenticationService;
import com.abs.restaurant.app.security.auth.RegisterRequest;
import com.abs.restaurant.app.service.IEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.util.Collections;

import static com.abs.restaurant.app.util.EntityMock.getInstance;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    private IEmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository, authService);
    }

    @Test
    public void findByNameOrLastnameTest() throws IOException {
        when(employeeRepository.findByNameOrLastname(anyString(), any()))
                .thenReturn(getInstance().getPageableEmployees());

        Page<Employee> lista = employeeService.findByNameOrLastname("ander", 1, 8);

        assertNotNull(lista);
        assertNotNull(lista.getContent());
        assertNotNull(lista.getContent().get(0));
        assertNotNull(lista.getContent().get(0).getId());
        assertNotNull(lista.getContent().get(0).getUser());
        assertNotNull(lista.getContent().get(0).getUser().getId());
        assertNotNull(lista.getContent().get(0).getUser().getFirstname());
        assertNotNull(lista.getContent().get(0).getUser().getLastname());
        assertNotNull(lista.getContent().get(0).getUser().getEmail());
        assertNotNull(lista.getContent().get(0).getUser().getPassword());
        assertNotNull(lista.getContent().get(0).getUser().getDni());
        assertNotNull(lista.getContent().get(0).getUser().getPhoneNumber());
        assertNotNull(lista.getContent().get(0).getUser().getRoles());
    }

    @Test
    public void findByNameOrLastnameEmptyTest() {
        when(employeeRepository.findByNameOrLastname(anyString(), any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<Employee> lista = employeeService.findByNameOrLastname("empty", 1, 8);

        assertNotNull(lista);
        assertNotNull(lista.getContent());
        assertTrue(lista.getContent().isEmpty());
    }

    @Test
    public void createEmployeeTest() throws IOException {
        when(authService.register(any(RegisterRequest.class)))
                .thenReturn(getInstance().createEmployee().getUser());

        employeeService.createEmployee(getInstance().requestUser());

        verify(authService).register(any(RegisterRequest.class));
        verify(employeeRepository).save(any(Employee.class));
    }
}