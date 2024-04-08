package com.abs.restaurant.app.controller.impl;

import com.abs.restaurant.app.controller.ProductController;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.mapper.IProductMapper;
import com.abs.restaurant.app.security.service.impl.JwtService;
import com.abs.restaurant.app.service.impl.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.*;

import static com.abs.restaurant.app.util.EntityMock.getInstance;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@WithMockUser
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private IProductMapper productMapper;
    @MockBean
    private JwtService jwtService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void findProductByIdTest() throws Exception {
        when(productService.findProductById(1L)).thenReturn(Optional.of(getInstance().createProduct()));
        when(productMapper.mapProductToProductDto(any(Product.class)))
                .thenReturn(getInstance().getProductDto());

        mvc.perform(get("/api/products/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.name").value("Cheese Hamburger"))
                .andExpect(jsonPath("$.description").value("Delicious hamburger with french fries, tomato, lettuce and sauces"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(20.30)))
                .andExpect(jsonPath("$.category.categoryId").value(1));

        verify(productService).findProductById(1L);
        verify(productMapper).mapProductToProductDto(any(Product.class));
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
        when(productMapper.mapProductRegistrationRequestToProduct(any(ProductRegistrationRequest.class)))
                .thenReturn(getInstance().createProduct());

        ProductRegistrationRequest input = getInstance().productRegistrationRequest();

        mvc.perform(post("/api/products")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated());

        verify(productService).createProduct(any(Product.class));
        verify(productMapper).mapProductRegistrationRequestToProduct(any(ProductRegistrationRequest.class));
    }

    @Test
    public void importProductsFromExcel() throws Exception {
        when(productService.getProductsFromExcel(any())).thenReturn(getInstance().getPageableProducts().getContent());

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "products.xlsx",
                "application/x-xls",
                new ClassPathResource("products.xlsx").getInputStream());

        mvc.perform(MockMvcRequestBuilders.multipart("/api/products/import")
                        .file(mockMultipartFile))
                .andExpect(status().isOk());

        verify(productService).getProductsFromExcel(any());
        verify(productMapper).mapProductToProductDto(any(Product.class));

    }

    @Test
    public void updateProductTest() throws Exception {
        when(productMapper.mapProductUpdateRequestToProduct(any(ProductUpdateRequest.class)))
                .thenReturn(getInstance().createProduct());
        when(productService.updateProduct(any(Product.class), anyLong())).thenReturn(getInstance().createProduct());
        ProductUpdateRequest input = getInstance().updateProduct();

        mvc.perform(put("/api/products/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());

        verify(productService).updateProduct(any(Product.class), anyLong());
        verify(productMapper).mapProductUpdateRequestToProduct(any(ProductUpdateRequest.class));
        verify(productMapper).mapProductToProductDto(any(Product.class));
    }

    @Test
    public void getProductsTest() throws Exception {
        Page<Product> input = getInstance().getPageableProducts();
        when(productService.getProducts(0,6)).thenReturn(input);
        when(productMapper.mapProductToProductDto(any(Product.class))).thenReturn(getInstance().getProductDto());

        mvc.perform(get("/api/products")
                .param("page","0")
                .param("size","6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        verify(productService).getProducts(0, 6);
        verify(productMapper).mapProductToProductDto(any(Product.class));
    }

    @Test
    public void getProductsEmptyResultTest() throws Exception {
        Page<Product> pageableProductsEmpty = new PageImpl<>(new ArrayList<>());
        when(productService.getProducts(2,4)).thenReturn(pageableProductsEmpty);

        mvc.perform(get("/api/products")
                        .param("page","2")
                        .param("size","4"))
                .andExpect(status().isNoContent());

        verify(productService).getProducts(2, 4);
        verify(productMapper, never()).mapProductToProductDto(any(Product.class));
    }

    @Test
    public void getProductsFilterByNameTest() throws Exception {
        when(productService.getProducts("prueba")).thenReturn(getInstance().getListProductsById());
        when(productMapper.mapProductToProductDto(any(Product.class))).thenReturn(getInstance().getProductDto());

        mvc.perform(get("/api/products/filter-by-name")
                        .param("name", "prueba"))
                .andExpect(status().isOk());

        verify(productService).getProducts("prueba");
        verify(productMapper, times(2)).mapProductToProductDto(any(Product.class));
    }

    @Test
    public void getProductsSearchTest() throws Exception {
        Product product = getInstance().createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Page<Product> pageableProducts = new PageImpl<>(products);
        when(productService.searchProducts("mix",1L,0,6)).thenReturn(pageableProducts);
        when(productMapper.mapProductToProductDto(any(Product.class))).thenReturn(getInstance().getProductDto());

        mvc.perform(get("/api/products/search")
                        .param("productName", "mix")
                        .param("categoryId", "1")
                        .param("page","0")
                        .param("size","6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        verify(productService).searchProducts("mix",1L,0, 6);
        verify(productMapper).mapProductToProductDto(any(Product.class));
    }

    @Test
    public void getProductsSearchEmptyResultTest() throws Exception {
        Page<Product> pageableProducts = new PageImpl<>(new ArrayList<>());
        when(productService.searchProducts("mix",1L,0,6)).thenReturn(pageableProducts);

        mvc.perform(get("/api/products/search")
                        .param("productName", "mix")
                        .param("categoryId", "1")
                        .param("page","0")
                        .param("size","6"))
                .andExpect(status().isNoContent());

        verify(productService).searchProducts("mix",1L,0, 6);
        verify(productMapper, never()).mapProductToProductDto(any(Product.class));
    }

    @Test
    public void updateProductsStockTest() throws Exception {
        when(productMapper.mapProductUpdateRequestToProduct(any(ProductUpdateRequest.class)))
                .thenReturn(getInstance().createProduct());
        when(productService.updateProductsStock(anyList())).thenReturn(Collections.singletonList(getInstance().createProduct()));

        List<ProductUpdateRequest> products = Collections.singletonList(getInstance().updateProduct());

        mvc.perform(put("/api/products/stock")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products)))
                .andExpect(status().isOk());

        verify(productService).updateProductsStock(anyList());
        verify(productMapper).mapProductToProductDto(any(Product.class));
    }

    @Test
    public void createProductsTest() throws Exception {
        when(productMapper.mapProductRegistrationRequestToProduct(any(ProductRegistrationRequest.class)))
                .thenReturn(getInstance().createProduct());

        List<ProductRegistrationRequest> products = getInstance().listRegistrationProducts();

        mvc.perform(post("/api/products/bulk")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products)))
                .andExpect(status().isCreated());

        verify(productService).createProducts(anyList());
        verify(productMapper, times(2)).mapProductRegistrationRequestToProduct(any(ProductRegistrationRequest.class));
    }

}