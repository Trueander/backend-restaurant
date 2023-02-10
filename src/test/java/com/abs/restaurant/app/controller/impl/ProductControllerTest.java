package com.abs.restaurant.app.controller.impl;

import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.mapper.impl.CategoryMapper;
import com.abs.restaurant.app.mapper.impl.ProductMapper;
import com.abs.restaurant.app.service.impl.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.abs.restaurant.app.util.EntityMock.getInstance;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void findProductByIdTest() throws Exception {
        when(productService.findProductById(1L)).thenReturn(Optional.of(getInstance().getProductDto()));

        mvc.perform(get("/api/products/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.name").value("Cheese Hamburger"))
                .andExpect(jsonPath("$.description").value("Delicious hamburger with french fries, tomato, lettuce and sauces"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(20.30)))
                .andExpect(jsonPath("$.category.categoryId").value(1));

        verify(productService).findProductById(1L);
    }

    @Test
    public void findProductByIdWrongIdTest() throws Exception {

        mvc.perform(get("/api/products/1").contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with ID: 1 not found."));

        verify(productService).findProductById(1L);
    }

    @Test
    public void createProductTest() throws Exception {
        ProductRegistrationRequest input = getInstance().productRegistrationRequest();

        when(productService.createProduct(any(ProductRegistrationRequest.class)))
                .thenReturn(getInstance().getProductDto());

        mvc.perform(post("/api/products")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.name").value("Cheese Hamburger"))
                .andExpect(jsonPath("$.description").value("Delicious hamburger with french fries, tomato, lettuce and sauces"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(20.30)))
                .andExpect(jsonPath("$.category.categoryId").value(1));

        verify(productService).createProduct(any(ProductRegistrationRequest.class));
    }

    @Test
    public void updateProductTest() throws Exception {
        ProductUpdateRequest input = getInstance().updateProduct();

        when(productService.updateProduct(any(ProductUpdateRequest.class), anyLong()))
                .thenReturn(getInstance().getProductDto());

        mvc.perform(put("/api/products/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.name").value("Cheese Hamburger"))
                .andExpect(jsonPath("$.description").value("Delicious hamburger with french fries, tomato, lettuce and sauces"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(20.30)))
                .andExpect(jsonPath("$.category.categoryId").value(1));

        verify(productService).updateProduct(any(ProductUpdateRequest.class), anyLong());
    }

    @Test
    public void getProductsTest() throws Exception {
        CategoryMapper categoryMapper = new CategoryMapper();
        ProductMapper mapper = new ProductMapper(categoryMapper);
        Page<Product> input = getInstance().getPageableProducts();
        when(productService.getProducts(0,6)).thenReturn(input.map(mapper::mapProductToProductDto));

        mvc.perform(get("/api/products/")
                .param("page","0")
                .param("size","6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        verify(productService).getProducts(0, 6);
    }

    @Test
    public void getProductsEmptyResultTest() throws Exception {
        Page<ProductDto> pageableProductsEmpty = new PageImpl<>(new ArrayList<>());
        when(productService.getProducts(2,4)).thenReturn(pageableProductsEmpty);

        mvc.perform(get("/api/products")
                        .param("page","2")
                        .param("size","4"))
                .andExpect(status().isNoContent());

        verify(productService).getProducts(2, 4);
    }

    @Test
    public void getProductsSearchTest() throws Exception {
        ProductDto product = getInstance().getProductDto();
        List<ProductDto> products = new ArrayList<>();
        products.add(product);
        Page<ProductDto> pageableProducts = new PageImpl<>(products);
        when(productService.searchProducts("mix",1L,0,6)).thenReturn(pageableProducts);

        mvc.perform(get("/api/products/search")
                        .param("productName", "mix")
                        .param("categoryId", "1")
                        .param("page","0")
                        .param("size","6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        verify(productService).searchProducts("mix",1L,0, 6);
    }
    @Test
    public void getProductsSearchEmptyResultTest() throws Exception {
        Page<ProductDto> pageableProducts = new PageImpl<>(new ArrayList<>());
        when(productService.searchProducts("mix",1L,0,6)).thenReturn(pageableProducts);

        mvc.perform(get("/api/products/search")
                        .param("productName", "mix")
                        .param("categoryId", "1")
                        .param("page","0")
                        .param("size","6"))
                .andExpect(status().isNoContent());

        verify(productService).searchProducts("mix",1L,0, 6);
    }

}