package com.abs.restaurant.app.service;

import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IProductService {

    ProductDto createProduct(ProductRegistrationRequest productRequest);
    Optional<ProductDto> findProductById(Long productId);
    ProductDto updateProduct(ProductUpdateRequest productRequest, Long productId);

    Page<ProductDto> getProducts(int page, int size);

    Page<ProductDto> searchProducts(String productName, Long categoryId, Integer page, Integer size);

}
