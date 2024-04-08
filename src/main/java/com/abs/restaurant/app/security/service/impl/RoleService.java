package com.abs.restaurant.app.security.service.impl;

import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.repository.RoleRepository;
import com.abs.restaurant.app.security.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}