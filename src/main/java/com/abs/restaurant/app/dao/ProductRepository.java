package com.abs.restaurant.app.dao;

import com.abs.restaurant.app.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
