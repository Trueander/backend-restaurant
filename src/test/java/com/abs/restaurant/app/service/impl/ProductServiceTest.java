package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.dao.ProductRepository;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.exceptions.NotFoundException;
import com.abs.restaurant.app.util.EntityMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {


    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init() throws IOException {
        Product product = EntityMock.getInstance().getProductMock();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Page<Product> pro = Mockito.mock(Page.class);
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(pro);
    }

    @Test
    public void createProductTest() throws IOException {
        Product input = EntityMock.getInstance().getProductMock();
        input.setId(null);

        Product createdProduct = productService.createProduct(input);

        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());
        assertNotNull(createdProduct.getName());
        assertNotNull(createdProduct.getDescription());
        assertNotNull(createdProduct.getPrice());
        assertNotNull(createdProduct.getStock());
        assertNotNull(createdProduct.getImageUrl());
        assertNotNull(createdProduct.getIsActive());
        assertNotNull(createdProduct.getDiscount());
        assertNotNull(createdProduct.getCategory());
        assertNotNull(createdProduct.getCategory().getId());
        assertNotNull(createdProduct.getCategory().getName());

        assertEquals(input.getName(), createdProduct.getName());
        assertEquals(input.getDescription(), createdProduct.getDescription());
        assertEquals(input.getPrice().toPlainString(), createdProduct.getPrice().toPlainString());
        assertEquals(input.getImageUrl(), createdProduct.getImageUrl());
        assertEquals(input.getStock(), createdProduct.getStock());
        assertEquals(input.getIsActive(), createdProduct.getIsActive());
        assertEquals(input.getDiscount(), createdProduct.getDiscount());
        assertEquals(input.getCategory().getId(), createdProduct.getCategory().getId());
    }

    @Test
    public void findProductByIdTest() {
        Optional<Product> productDB = productService.findProductById(1L);

        assertTrue(productDB.isPresent());

        assertNotNull(productDB.get());
        assertNotNull(productDB.get().getId());
    }



    @Test
    public void updateProductTest() throws IOException {
        Product input = EntityMock.getInstance().getProductMock();
        Product productDB = productService.updateProduct(input,1L);

        assertNotNull(productDB);
        assertNotNull(productDB.getId());
        assertEquals(productDB.getImageUrl(), input.getImageUrl());
    }

    @Test
    public void updateProductWrongIdTest() throws IOException {
        Product input = EntityMock.getInstance().getProductMock();

        assertThrows(NotFoundException.class, () -> {
            productService.updateProduct(input,2L);
        });
    }

    @Test
    public void pageableProductsTest() {
        Page<Product> products = productService.getProducts(1, 5);

        assertNotNull(products);
        assertNotNull(products.getContent());

        Mockito.verify(productRepository).findAll(any(PageRequest.class));
    }
}