package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.dao.CategoryRepository;
import com.abs.restaurant.app.dao.ProductRepository;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.exceptions.ResourceNotFoundException;
import com.abs.restaurant.app.mapper.ICategoryMapper;
import com.abs.restaurant.app.mapper.impl.CategoryMapper;
import com.abs.restaurant.app.mapper.impl.ProductMapper;
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

    private final ICategoryMapper categoryMapper = new CategoryMapper();

    private final ProductMapper mapper = new ProductMapper(categoryMapper);


    @BeforeEach
    public void init() {
        productService = new ProductService(productRepository, categoryRepository, mapper);
    }

    @Test
    public void createProductTest() throws IOException {
        when(productRepository.save(any(Product.class))).thenReturn(getInstance().createProduct());
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(getInstance().getCategory()));

        ProductRegistrationRequest input = getInstance().productRegistrationRequest();
        ProductDto createdProduct = productService.createProduct(input);

        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getProductId());
        assertNotNull(createdProduct.getName());
        assertNotNull(createdProduct.getDescription());
        assertNotNull(createdProduct.getPrice());
        assertNotNull(createdProduct.getStock());
        assertNotNull(createdProduct.getImageUrl());
        assertNotNull(createdProduct.getIsActive());
        assertNotNull(createdProduct.getDiscount());
        assertNotNull(createdProduct.getCategory());
        assertNotNull(createdProduct.getCategory().getCategoryId());
        assertNotNull(createdProduct.getCategory().getName());

        assertEquals(input.getName(), createdProduct.getName());
        assertEquals(input.getDescription(), createdProduct.getDescription());
        assertEquals(input.getPrice().toPlainString(), createdProduct.getPrice().toPlainString());
        assertEquals(input.getImageUrl(), createdProduct.getImageUrl());
        assertEquals(input.getStock(), createdProduct.getStock());
        assertEquals(true, createdProduct.getIsActive());
        assertEquals(input.getDiscount(), createdProduct.getDiscount());
        assertEquals(input.getCategoryId(), createdProduct.getCategory().getCategoryId());

        verify(productRepository).save(any(Product.class));
        verify(categoryRepository).findById(1L);
    }

    @Test
    public void createProductNullTest() {
        ProductDto result = productService.createProduct(null);

        assertNull(result);
    }

    @Test
    public void createProductWithWrongCategoryIdTest() throws IOException {
        ProductRegistrationRequest input = getInstance().productRegistrationRequest();
        ResourceNotFoundException errorMessage = assertThrows(ResourceNotFoundException.class,
                () -> productService.createProduct(input));

        assertEquals("Category with ID: 1 not found", errorMessage.getMessage());
    }

    @Test
    public void findProductByIdTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(EntityMock.getInstance().createProduct()));

        Optional<ProductDto> productDB = productService.findProductById(1L);

        assertTrue(productDB.isPresent());
        assertNotNull(productDB.get());
        assertNotNull(productDB.get().getProductId());
        assertNotNull(productDB.get().getDescription());
        assertNotNull(productDB.get().getPrice());
        assertNotNull(productDB.get().getStock());
        assertNotNull(productDB.get().getImageUrl());
        assertNotNull(productDB.get().getIsActive());
        assertNotNull(productDB.get().getDiscount());
        assertNotNull(productDB.get().getCategory());
        assertNotNull(productDB.get().getCategory().getCategoryId());
        assertNotNull(productDB.get().getCategory().getName());
    }

    @Test
    public void findProductByIdNotFoundTest() {
        Optional<ProductDto> foundProduct = productService.findProductById(2L);

        assertTrue(foundProduct.isEmpty());
        verify(productRepository).findById(2L);
    }

    @Test
    public void updateProductTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(getInstance().createProduct()));
        when(productRepository.save(any(Product.class))).thenReturn(getInstance().createProduct());

        ProductUpdateRequest input = getInstance().updateProduct();
        ProductDto productDB = productService.updateProduct(input,1L);

        assertNotNull(productDB);
        assertNotNull(productDB.getProductId());
        assertNotNull(productDB.getName());
        assertNotNull(productDB.getDescription());
        assertNotNull(productDB.getPrice());
        assertNotNull(productDB.getStock());
        assertNotNull(productDB.getImageUrl());
        assertNotNull(productDB.getIsActive());
        assertNotNull(productDB.getDiscount());
        assertNotNull(productDB.getCategory());
        assertNotNull(productDB.getCategory().getCategoryId());
        assertNotNull(productDB.getCategory().getName());
    }

    @Test
    public void updateProductWithDifferentCategoryIdTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(getInstance().createProduct()));
        when(productRepository.save(any(Product.class))).thenReturn(getInstance().createProduct());
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(getInstance().getCategory()));

        ProductUpdateRequest input = getInstance().updateProduct();
        input.setCategoryId(2L);
        ProductDto productDB = productService.updateProduct(input,1L);

        assertNotNull(productDB);
        assertNotNull(productDB.getProductId());
        assertNotNull(productDB.getName());
        assertNotNull(productDB.getDescription());
        assertNotNull(productDB.getPrice());
        assertNotNull(productDB.getStock());
        assertNotNull(productDB.getImageUrl());
        assertNotNull(productDB.getIsActive());
        assertNotNull(productDB.getDiscount());
        assertNotNull(productDB.getCategory());
        assertNotNull(productDB.getCategory().getCategoryId());
        assertNotNull(productDB.getCategory().getName());
    }

    @Test
    public void updateProductWrongProductIdTest() throws IOException {
        ProductUpdateRequest input = getInstance().updateProduct();

        ResourceNotFoundException errorMessage = assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(input, 2L));

        assertEquals("Product with ID: 2 not found", errorMessage.getMessage());
    }

    @Test
    public void updateProductNullTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(getInstance().createProduct()));

        ProductDto result = productService.updateProduct(null, 1L);

        assertNull(result);

        verify(productRepository).findById(1L);
    }

    @Test
    public void updateProductWrongCategoryIdTest() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(getInstance().createProduct()));

        ProductUpdateRequest input = getInstance().updateProduct();
        input.setCategoryId(4L);

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(input,1L);
        });

        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(4L);
    }


    @Test
    public void pageableProductsTest() throws IOException {
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(getInstance().getPageableProducts());

        Page<ProductDto> products = productService.getProducts(1, 5);

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

        Page<ProductDto> filteredProducts = productService.searchProducts("mix", 1L, 1, 5);

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

        Page<ProductDto> filteredProducts = productService.searchProducts("mix", null, 1, 5);

        assertNotNull(filteredProducts);
        assertNotNull(filteredProducts.getContent());

        verify(productRepository).findByNameContainingIgnoreCase("mix", PageRequest.of(1,5));
        verify(categoryRepository, never()).findById(anyLong());
        verify(productRepository, never()).findByNameContainingIgnoreCaseAndCategoryId(anyString(), anyLong(), any(PageRequest.class));
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
}