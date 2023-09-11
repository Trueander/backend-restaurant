package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.dao.CategoryRepository;
import com.abs.restaurant.app.dao.ProductRepository;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.exceptions.ResourceNotFoundException;
import com.abs.restaurant.app.mapper.IExcelToProductMapper;
import com.abs.restaurant.app.service.IProductService;
import com.abs.restaurant.app.util.EntityMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.abs.restaurant.app.util.EntityMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private IProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IExcelToProductMapper excelToProductMapper;


    @BeforeEach
    public void init() {
        productService = new ProductService(productRepository, categoryRepository, excelToProductMapper);
    }

    @Test
    public void createProductTest() throws IOException {
        when(productRepository.save(any(Product.class))).thenReturn(getInstance().createProduct());
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(getInstance().getCategory()));

        productService.createProduct(getInstance().createProduct());

        verify(productRepository).save(any(Product.class));
        verify(categoryRepository).findById(1L);
    }

    @Test
    public void createProductWithWrongCategoryIdTest() throws IOException {
        Product input = getInstance().createProduct();
        ResourceNotFoundException errorMessage = assertThrows(ResourceNotFoundException.class,
                () -> productService.createProduct(input));

        assertEquals("Category with ID: 1 not found", errorMessage.getMessage());
    }

    @Test
    public void findProductByIdTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(EntityMock.getInstance().createProduct()));

        Optional<Product> productDB = productService.findProductById(1L);

        assertTrue(productDB.isPresent());
        assertNotNull(productDB.get());
        assertNotNull(productDB.get().getId());
        assertNotNull(productDB.get().getDescription());
        assertNotNull(productDB.get().getPrice());
        assertNotNull(productDB.get().getStock());
        assertNotNull(productDB.get().getImageUrl());
        assertNotNull(productDB.get().getIsActive());
        assertNotNull(productDB.get().getDiscount());
        assertNotNull(productDB.get().getCategory());
        assertNotNull(productDB.get().getCategory().getId());
        assertNotNull(productDB.get().getCategory().getName());
    }

    @Test
    public void findProductByIdNotFoundTest() {
        Optional<Product> foundProduct = productService.findProductById(2L);

        assertTrue(foundProduct.isEmpty());
        verify(productRepository).findById(2L);
    }

    @Test
    public void updateProductTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(getInstance().createProduct()));
        when(productRepository.save(any(Product.class))).thenReturn(getInstance().createProduct());

        Product product = productService.updateProduct(getInstance().createProduct(), 1L);

        assertNotNull(product);

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
        verify(categoryRepository, never()).findById(1L);
    }

    @Test
    public void updateProductWithDifferentCategoryIdTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(getInstance().createProduct()));
        when(productRepository.save(any(Product.class))).thenReturn(getInstance().createProduct());
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(getInstance().getCategory()));

        Product input = getInstance().createProduct();
        input.getCategory().setId(2L);
        productService.updateProduct(input,1L);

        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(2L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void updateProductWrongProductIdTest() throws IOException {
        Product input = getInstance().createProduct();

        ResourceNotFoundException errorMessage = assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(input, 2L));

        assertEquals("Product with ID: 2 not found", errorMessage.getMessage());
    }


    @Test
    public void updateProductWrongCategoryIdTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(getInstance().createProduct()));

        Product input = getInstance().createProduct();
        input.getCategory().setId(4L);

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(input,1L);
        });

        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(4L);
    }


    @Test
    public void pageableProductsTest() throws IOException {
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(getInstance().getPageableProducts());

        Page<Product> products = productService.getProducts(1, 5);

        assertNotNull(products);
        assertNotNull(products.getContent());

        verify(productRepository).findAll(any(PageRequest.class));
    }

    @Test
    public void searchProductsTestWithCategoryId() throws IOException {
        when(productRepository
                .findByNameContainingIgnoreCaseAndCategoryId(anyString(), anyLong(), any(PageRequest.class)))
                .thenReturn(getInstance().getPageableProducts());
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(getInstance().getCategory()));

        Page<Product> filteredProducts = productService.searchProducts("mix", 1L, 1, 5);

        assertNotNull(filteredProducts);
        assertNotNull(filteredProducts.getContent());

        verify(categoryRepository).findById(1L);
        verify(productRepository).findByNameContainingIgnoreCaseAndCategoryId("mix", 1L, PageRequest.of(1,5));
        verify(productRepository, never()).findByNameContainingIgnoreCase(anyString(), any(PageRequest.class));
    }

    @Test
    public void searchProductsTestWithOutCategoryId() throws IOException {
        when(productRepository
                .findByNameContainingIgnoreCase(anyString(), any(PageRequest.class)))
                .thenReturn(getInstance().getPageableProducts());

        Page<Product> filteredProducts = productService.searchProducts("mix", null, 1, 5);

        assertNotNull(filteredProducts);
        assertNotNull(filteredProducts.getContent());

        verify(productRepository).findByNameContainingIgnoreCase("mix", PageRequest.of(1,5));
        verify(categoryRepository, never()).findById(anyLong());
        verify(productRepository, never()).findByNameContainingIgnoreCaseAndCategoryId(anyString(), anyLong(), any(PageRequest.class));
    }

    @Test
    public void getProductsTest() {
        when(productRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(getInstance().getListProductsById());

        List<Product> productList = productService.getProducts("prueba");

        assertNotNull(productList);
        assertNotNull(productList.get(0));
        assertNotNull(productList.get(0).getId());
        assertNotNull(productList.get(0).getName());
        assertNotNull(productList.get(0).getStock());
        assertNotNull(productList.get(1));
        assertNotNull(productList.get(1).getId());
        assertNotNull(productList.get(1).getName());
        assertNotNull(productList.get(1).getStock());
        assertEquals(2, productList.size());

        verify(productRepository).findByNameContainingIgnoreCase(anyString());
    }

    @Test
    public void searchProductsTestWithCategoryIdNotFound() {

        ResourceNotFoundException errorMessage = assertThrows(ResourceNotFoundException.class, () -> {
            productService.searchProducts("mix", 2L, 1, 5);
        });

        assertEquals("Category with ID: 2 not found", errorMessage.getMessage());

        verify(categoryRepository).findById(2L);
        verify(productRepository, never()).findByNameContainingIgnoreCaseAndCategoryId("mix",2L, PageRequest.of(1,5));
        verify(productRepository, never()).findByNameContainingIgnoreCase("mix", PageRequest.of(1,5));
    }

    @Test
    public void updateProductsStockTest() {
        when(productRepository.findAllById(anyList())).thenReturn(getInstance().getListProductsById());
        when(productRepository.saveAll(anyList())).thenReturn(getInstance().getListProductsById());

        List<Product> result = productService.updateProductsStock(getInstance().getStockProductsRequest());

        assertNotNull(result);
        assertNotNull(result.get(0));
        assertNotNull(result.get(0).getId());
        assertNotNull(result.get(0).getName());
        assertNotNull(result.get(0).getStock());
        assertNotNull(result.get(1));
        assertNotNull(result.get(1).getId());
        assertNotNull(result.get(1).getName());
        assertNotNull(result.get(1).getStock());

        verify(productRepository).findAllById(anyList());
        verify(productRepository).saveAll(anyList());
    }

    @Test
    public void createProductsTest() {

        productService.createProducts(getInstance().listProducts());

        verify(productRepository).saveAll(anyList());
    }
}