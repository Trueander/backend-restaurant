package com.abs.restaurant.app.util;

import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.ProductDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.domain.Page;

import java.io.IOException;

public class EntityMock {

    private final static EntityMock INSTANCE = new EntityMock();

    private final ObjectMapperHelper objectMapperHelper;

    private EntityMock() {
        this.objectMapperHelper =ObjectMapperHelper.getInstance();
    }

    public static EntityMock getInstance() {
        return INSTANCE;
    }

    public ProductDto getProducDtoMock() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/products/createProductDto.json"), ProductDto.class);
    }

    public Product getProductMock() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/products/createProduct.json"), Product.class);
    }

//    public Page<ProductDto> getProductsPageableMock() throws IOException {
//        return objectMapperHelper.readValue(Thread.currentThread()
//                .getContextClassLoader()
//                .getResourceAsStream("mock/products/getProductPageable.json"),
//                TypeReference <Page<ProductDto>>() {});
//    }

}
