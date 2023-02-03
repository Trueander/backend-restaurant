package com.abs.restaurant.app.mapper;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;

public interface IProductMapper {

    Product mapProductRegistrationRequestToProduct(ProductRegistrationRequest dtoIn);
    Product mapProductUpdateRequestToProduct(ProductUpdateRequest dtoIn);

    ProductDto mapProductToProductDto(Product product);


}
