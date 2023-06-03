package com.abs.restaurant.app.dao;

import com.abs.restaurant.app.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(String productName, Long categoryId, PageRequest pageRequest);
    Page<Product> findByNameContainingIgnoreCase(String productName, PageRequest pageRequest);
    List<Product> findByNameContainingIgnoreCase(String nombre);
}
