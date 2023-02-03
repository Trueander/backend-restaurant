package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.mapper.ICategoryMapper;
import com.abs.restaurant.app.util.EntityMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductMapperImplTest {


    private final ICategoryMapper categoryMapper = new CategoryMapper();

    private final ProductMapper productMapper = new ProductMapper(categoryMapper);

    @Test
    public void mapProductRegistrationRequestToProductTest() throws IOException {
        ProductRegistrationRequest productDto = EntityMock.getInstance().productRegistrationRequest();

        Product result = productMapper.mapProductRegistrationRequestToProduct(productDto);

        assertNotNull(result);
        assertNotNull(result.getName());
        assertNotNull(result.getDescription());
        assertNotNull(result.getPrice());
        assertNotNull(result.getStock());
        assertNotNull(result.getImageUrl());
        assertNotNull(result.getIsActive());
        assertNotNull(result.getDiscount());
        assertNotNull(result.getCategory());
        assertNotNull(result.getCategory().getId());

        assertEquals(productDto.getName(), result.getName());
        assertEquals(productDto.getDescription(), result.getDescription());
        assertEquals(productDto.getPrice().toPlainString(), result.getPrice().toPlainString());
        assertEquals(productDto.getImageUrl(), result.getImageUrl());
        assertEquals(productDto.getStock(), result.getStock());
        assertEquals(true, result.getIsActive());
        assertEquals(productDto.getDiscount(), result.getDiscount());
        assertEquals(productDto.getCategoryId(), result.getCategory().getId());
    }

    @Test
    public void mapProductToProductDtoTest() throws IOException {
        Product product = EntityMock.getInstance().createProduct();

        ProductDto result = productMapper.mapProductToProductDto(product);

        assertNotNull(result);
        assertNotNull(result.getProductId());
        assertNotNull(result.getName());
        assertNotNull(result.getDescription());
        assertNotNull(result.getPrice());
        assertNotNull(result.getStock());
        assertNotNull(result.getImageUrl());
        assertNotNull(result.getIsActive());
        assertNotNull(result.getDiscount());
        assertNotNull(result.getCategory());
        assertNotNull(result.getCategory().getCategoryId());
        assertNotNull(result.getCategory().getName());

        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getPrice().toPlainString(), result.getPrice().toPlainString());
        assertEquals(product.getImageUrl(), result.getImageUrl());
        assertEquals(product.getStock(), result.getStock());
        assertEquals(product.getIsActive(), result.getIsActive());
        assertEquals(product.getDiscount(), result.getDiscount());
        assertEquals(product.getCategory().getId(), result.getCategory().getCategoryId());
        assertEquals(product.getCategory().getName(), result.getCategory().getName());
    }
}