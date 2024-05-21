package com.abs.restaurant.app.util;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Employee;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.security.auth.RegisterRequest;
import com.abs.restaurant.app.security.entity.Role;
import com.abs.restaurant.app.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityMock {

    private final static EntityMock INSTANCE = new EntityMock();

    private final ObjectMapperHelper objectMapperHelper;

    private EntityMock() {
        this.objectMapperHelper =ObjectMapperHelper.getInstance();
    }

    public static EntityMock getInstance() {
        return INSTANCE;
    }

    public Category getCategory() {
        Category category= new Category();
        category.setId(1L);
        category.setName("Main course");
        category.setIcon("local_bar");
        category.setIsActive(true);
        return  category;
    }

    public Page<Category> getPageableCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(getCategory());
        return new PageImpl<>(categoryList);
    }

    public CategoryRegistrationRequest categoryRegistrationRequest() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/category/createCategory.json"), CategoryRegistrationRequest.class);
    }

    public CategoryUpdateRequest categoryUpdateRequest() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/category/updateCategory.json"), CategoryUpdateRequest.class);
    }

    public CategoryDto getCategoryDto() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/category/getCategoryDto.json"), CategoryDto.class);
    }

    public Page<Product> getPageableProducts() throws IOException {
        List<Product> productsList = new ArrayList<>();
        productsList.add(createProduct());
        return new PageImpl<>(productsList);
    }

    public List<ProductUpdateRequest> getStockProductsRequest() {
        return Arrays.asList(ProductUpdateRequest.builder().productId(1L).stock(5).build(),
                ProductUpdateRequest.builder().productId(2L).stock(5).build());
    }

    public List<Product> getListProductsById() {
        return Arrays.asList(Product.builder().id(1L).name("Hamburger").stock(5).build(),
                Product.builder().id(2L).name("Pizza").stock(5).build());
    }

    public ProductRegistrationRequest productRegistrationRequest() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/products/createProductDto.json"), ProductRegistrationRequest.class);
    }

    public List<ProductRegistrationRequest> listRegistrationProducts() throws IOException {
        return Arrays.asList(
                ProductRegistrationRequest.builder().name("Hamburger").description("Delicious")
                        .price(new BigDecimal("22.5")).stock(5).categoryId(1L).build(),
                ProductRegistrationRequest.builder().name("Hamburger").description("Delicious")
                        .price(new BigDecimal("22.5")).stock(5).categoryId(1L).build());
    }

    public List<Product> listProducts() {
        Category category = new Category();
        category.setId(1L);
        return Arrays.asList(Product.builder().name("Hamburger").description("Delicious")
                        .price(new BigDecimal("22.5")).stock(5).category(category).build(),
                Product.builder().name("Hamburger").description("Delicious")
                        .price(new BigDecimal("22.5")).stock(5).category(category).build());
    }

    public Product createProduct() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/products/createProduct.json"), Product.class);
    }

    public ProductDto getProductDto() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/products/getProductDto.json"), ProductDto.class);
    }

    public ProductUpdateRequest updateProduct() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/products/updateProduct.json"), ProductUpdateRequest.class);
    }

    public RegisterRequest requestUser() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/user/createUser.json"), RegisterRequest.class);
    }

    public User getUser() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/user/getUser.json"), User.class);
    }

    public Employee createEmployee() throws IOException {
        return objectMapperHelper.readValue(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mock/employee/createEmployee.json"), Employee.class);
    }

    public Page<Employee> getPageableEmployees() throws IOException {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(createEmployee());
        return new PageImpl<>(employeeList);
    }

    public List<Role> getRoles() {
        return Arrays.asList(
                new Role(1, "ADMIN"),
                new Role(2, "WAITER")
        );
    }
}
