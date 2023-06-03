package com.abs.restaurant.app.service;

import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    void createProduct(Product product);
    Optional<Product> findProductById(Long productId);
    Product updateProduct(Product product, Long productId);

    Page<Product> getProducts(int page, int size);

    List<Product> getProducts(String productName);

    Page<Product> searchProducts(String productName, Long categoryId, Integer page, Integer size);

    List<Product> updateProductsStock(List<ProductUpdateRequest> productsDto);

    List<Product> getProductsFromExcel(MultipartFile excelProductsData);

    void createProducts(List<Product> products);
}
