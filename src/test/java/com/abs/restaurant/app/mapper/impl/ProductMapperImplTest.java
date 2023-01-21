package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.ProductDto;
import com.abs.restaurant.app.mapper.IProductMapper;
import com.abs.restaurant.app.util.EntityMock;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperImplTest {

    @InjectMocks
    private ProductMapper productMapper;

    @Test
    public void mapProductDtoToProductTest() throws IOException {
        ProductDto productDto = EntityMock.getInstance().getProducDtoMock();

        Product result = productMapper.mapProductDtoToProduct(productDto);

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
        assertEquals(productDto.getIsActive(), result.getIsActive());
        assertEquals(productDto.getDiscount(), result.getDiscount());
        assertEquals(productDto.getCategory().getCategoryId(), result.getCategory().getId());
    }

    @Test
    public void mapProductToProductDtoTest() throws IOException {
        Product product = EntityMock.getInstance().getProductMock();

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