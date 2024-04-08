package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.repository.EmployeeRepository;
import com.abs.restaurant.app.security.auth.AuthenticationService;
import com.abs.restaurant.app.security.auth.RegisterRequest;
import com.abs.restaurant.app.security.entity.User;
import com.abs.restaurant.app.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthenticationService authService;

    @Override
    public Page<Employee> findByNameOrLastname(String criteria, Integer page, Integer size) {
        log.info("... invoking method EmployeeService.findByNameOrLastname ...");
        PageRequest pageRequest = PageRequest.of(page, size);
        return employeeRepository.findByNameOrLastname(criteria, pageRequest);
    }

    @Override
    public void createEmployee(RegisterRequest request) {
        log.info("... invoking method EmployeeService.createEmployee ...");
        User user = authService.register(request);
        Employee employee = Employee.builder()
                .user(user)
                .build();

        employeeRepository.save(employee);
    }
}