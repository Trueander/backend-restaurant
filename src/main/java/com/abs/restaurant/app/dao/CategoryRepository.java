package com.abs.restaurant.app.dao;

import com.abs.restaurant.app.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContainingIgnoreCase(String categoryName, PageRequest pageRequest);
}
