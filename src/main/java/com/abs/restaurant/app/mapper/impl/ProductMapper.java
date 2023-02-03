package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.mapper.ICategoryMapper;
import com.abs.restaurant.app.mapper.IProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductMapper implements IProductMapper {

    private final ICategoryMapper categoryMapper;

    @Override
    public Product mapProductRegistrationRequestToProduct(ProductRegistrationRequest dtoIn) {
        if(dtoIn == null) return null;

        Product product = new Product();
        product.setName(dtoIn.getName());
        product.setDescription(dtoIn.getDescription());
        product.setPrice(dtoIn.getPrice());
        product.setStock(dtoIn.getStock());
        product.setDiscount(dtoIn.getDiscount());
        product.setImageUrl(dtoIn.getImageUrl());
        product.setIsActive(true);
        product.setCategory(mapInCategory(dtoIn.getCategoryId()));

        return product;
    }

    @Override
    public Product mapProductUpdateRequestToProduct(ProductUpdateRequest dtoIn) {
        if(dtoIn == null) return null;

        Product product = new Product();
        product.setId(dtoIn.getProductId());
        product.setName(dtoIn.getName());
        product.setDescription(dtoIn.getDescription());
        product.setPrice(dtoIn.getPrice());
        product.setStock(dtoIn.getStock());
        product.setDiscount(dtoIn.getDiscount());
        product.setImageUrl(dtoIn.getImageUrl());
        product.setIsActive(true);
        product.setCategory(mapInCategory(dtoIn.getCategoryId()));

        return product;
    }

    @Override
    public ProductDto mapProductToProductDto(Product product) {
        if(product == null) return null;

        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setIsActive(product.getIsActive());
        productDto.setDiscount(product.getDiscount());
        productDto.setCategory(categoryMapper.mapCategoryToCategoryDto(product.getCategory()));

        return productDto;
    }

    private Category mapInCategory(Long categoryId) {
        if(categoryId == null) return null;

        Category category = new Category();
        category.setId(categoryId);
        return category;
    }
}
