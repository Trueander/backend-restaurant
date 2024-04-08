package com.abs.restaurant.app.repository;

import com.abs.restaurant.app.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("FROM Employee u WHERE LOWER(u.user.firstname) LIKE %:criteria% or LOWER(u.user.lastname) LIKE %:criteria%")
    Page<Employee> findByNameOrLastname(String criteria, PageRequest pageRequest);
}