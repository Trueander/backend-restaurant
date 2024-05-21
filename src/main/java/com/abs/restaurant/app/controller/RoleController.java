package com.abs.restaurant.app.controller;

import com.abs.restaurant.app.mapper.IRoleMapper;
import com.abs.restaurant.app.security.entity.dto.RoleDto;
import com.abs.restaurant.app.security.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;
    private final IRoleMapper roleMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleDto> roles() {
        return roleService.getRoles()
                .stream()
                .map(roleMapper::mapRoleToRoleDto)
                .collect(Collectors.toList());
    }
}