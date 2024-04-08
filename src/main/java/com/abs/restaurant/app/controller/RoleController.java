package com.abs.restaurant.app.controller;

import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> roles() {
        return roleService.getRoles();
    }
}