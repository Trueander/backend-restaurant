package com.abs.restaurant.app.controller;

import com.abs.restaurant.app.utils.Utils;
import com.abs.restaurant.app.entity.dto.employee.EmployeeDto;
import com.abs.restaurant.app.mapper.IEmployeeMapper;
import com.abs.restaurant.app.security.auth.RegisterRequest;
import com.abs.restaurant.app.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final IEmployeeService employeeService;
    private final IEmployeeMapper employeeMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<EmployeeDto> usersByNameOrLastname(@RequestParam(name = "criteria") String criteria,
                                                   @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(name = "size", defaultValue = "8") Integer size) {
        return employeeService.findByNameOrLastname(criteria, page, size)
                .map(employeeMapper::mapEmployeeToEmployeeDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(Utils.validateRequestErrors(result));
        }

        employeeService.createEmployee(request);

        return ResponseEntity.status(CREATED).build();
    }
}
