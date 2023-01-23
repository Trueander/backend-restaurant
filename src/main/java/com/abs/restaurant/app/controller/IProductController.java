package com.abs.restaurant.app.controller;

import com.abs.restaurant.app.entity.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;

public interface IProductController {

    ResponseEntity<?> createProduct(ProductDto productDto, BindingResult result);

    ResponseEntity<?> findProductById(@NotNull Long productId);

    ResponseEntity<?> updateProduct(@NotNull Long productId, ProductDto productDto, BindingResult result);

    ResponseEntity<?> getProducts(Integer page, Integer size);
}
