package com.abs.restaurant.app.mapper;

import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.ProductDto;

public interface IProductMapper {

    Product mapProductDtoToProduct(ProductDto dtoIn);

    ProductDto mapProductToProductDto(Product product);
}
