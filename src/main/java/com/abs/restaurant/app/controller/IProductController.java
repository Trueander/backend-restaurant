package com.abs.restaurant.app.controller;

import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IProductController {

    ResponseEntity<?> createProduct(ProductRegistrationRequest productDto, BindingResult result);

    ResponseEntity<?> findProductById(@NotNull Long productId);

    ResponseEntity<?> updateProduct(@NotNull Long productId, ProductUpdateRequest productDto, BindingResult result);

    ResponseEntity<?> getProducts(Integer page, Integer size);

    ResponseEntity<?> filterProducts(String productName, Long categoryId, Integer page, Integer size);

    ResponseEntity<?> updateProductsStock(List<ProductUpdateRequest> productsDto);
}
