package com.abs.restaurant.app.security.repository;

import com.abs.restaurant.app.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("FROM Role r WHERE r.id in :roleIds")
    List<Role> findRolesByIds(Set<Integer> roleIds);
}
