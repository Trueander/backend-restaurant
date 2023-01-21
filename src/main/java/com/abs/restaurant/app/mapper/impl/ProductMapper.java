package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.CategoryDto;
import com.abs.restaurant.app.entity.dto.ProductDto;
import com.abs.restaurant.app.mapper.IProductMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements IProductMapper {

    @Override
    public Product mapProductDtoToProduct(ProductDto dtoIn) {
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
        product.setCategory(mapCategoryDtoToCategory(dtoIn.getCategory()));

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
        productDto.setCategory(mapCategoryToCategoryDto(product.getCategory()));

        return productDto;
    }

    private Category mapCategoryDtoToCategory(CategoryDto dtoIn) {
        if(dtoIn == null) return null;

        Category category = new Category();
        category.setId(dtoIn.getCategoryId());
        return category;
    }

    private CategoryDto mapCategoryToCategoryDto(Category category) {
        if(category == null) return null;

        CategoryDto dtoOut = new CategoryDto();
        dtoOut.setCategoryId(category.getId());
        dtoOut.setName(category.getName());
        dtoOut.setIsActive(category.getIsActive());
        return dtoOut;
    }
}
