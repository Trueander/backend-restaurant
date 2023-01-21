package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.dao.CategoryRepository;
import com.abs.restaurant.app.dao.ProductRepository;
import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.exceptions.NotFoundException;
import com.abs.restaurant.app.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(Product product) {
        log.info("... invoking method ProduceServiceImpl.createProduct ...");
        Optional<Category> category = categoryRepository.findById(product.getCategory().getId());

        if(!category.isPresent()) throw new NotFoundException("Category with ID: " + product.getId() + " not found");

        product.setCategory(category.get());

        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findProductById(Long productId) {
        log.info("... invoking method ProduceServiceImpl.findProductById ...");
        return productRepository.findById(productId);
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        log.info("... invoking method ProduceServiceImpl.updateProduct ...");

        Optional<Product> productDB = findProductById(productId);
        if(!productDB.isPresent()) throw new NotFoundException("Product with ID: " + productId + " not found");
        BeanUtils.copyProperties(product, productDB.get());

        return productRepository.save(productDB.get());
    }

    @Override
    public Page<Product> getProducts(int page, int size) {
        log.info("... invoking method ProduceServiceImpl.getProducts ...");

        PageRequest pr = PageRequest.of(page,size);
        return productRepository.findAll(pr);
    }
}
