package com.abs.restaurant.app.dao;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Page<Category> findByNameContainingIgnoreCase(String categoryName, PageRequest pageRequest);
}
