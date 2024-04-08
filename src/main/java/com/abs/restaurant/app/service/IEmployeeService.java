package com.abs.restaurant.app.service;

import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.security.auth.RegisterRequest;
import org.springframework.data.domain.Page;

public interface IEmployeeService {
    Page<Employee> findByNameOrLastname(String criteria, Integer page, Integer size);
    void createEmployee(RegisterRequest request);
}