package com.abs.restaurant.app.service;

import com.abs.restaurant.app.entity.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IProductService {

    Product createProduct(Product product);
    Optional<Product> findProductById(Long productId);
    Product updateProduct(Product product, Long productId);

    Page<Product> getProducts(int page, int size);

}
