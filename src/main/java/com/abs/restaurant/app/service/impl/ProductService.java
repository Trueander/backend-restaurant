package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.dao.CategoryRepository;
import com.abs.restaurant.app.dao.ProductRepository;
import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.exceptions.ResourceNotFoundException;
import com.abs.restaurant.app.mapper.IProductMapper;
import com.abs.restaurant.app.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final IProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductRegistrationRequest productRequest) {
        log.info("... invoking method ProduceServiceImpl.createProduct ...");

        Product productMapped = productMapper.mapProductRegistrationRequestToProduct(productRequest);

        if(productMapped == null) return null;

        if(productMapped.getCategory() != null && productMapped.getCategory().getId() != null) {
            Category category = categoryRepository
                    .findById(productMapped.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException
                            ("Category with ID: " + productMapped.getCategory().getId() + " not found"));
            productMapped.setCategory(category);
        }

        return productMapper.mapProductToProductDto(productRepository.save(productMapped));
    }

    @Override
    public Optional<ProductDto> findProductById(Long productId) {
        log.info("... invoking method ProduceServiceImpl.findProductById ...");
        return productRepository.findById(productId)
                .map(productMapper::mapProductToProductDto);
    }

    @Override
    public ProductDto updateProduct(ProductUpdateRequest productRequest, Long productId) {
        log.info("... invoking method ProduceServiceImpl.updateProduct ...");
        Product productDB = productRepository
                .findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + productId + " not found"));

        Product productMapped = productMapper.mapProductUpdateRequestToProduct(productRequest);

        if(productMapped == null) return null;

        if(productMapped.getCategory() != null &&
                productMapped.getCategory().getId() != null &&
                !productMapped.getCategory().getId().equals(productDB.getCategory().getId())) {

            Category category = categoryRepository.findById(productMapped.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException
                            ("Category with ID: " + productMapped.getCategory().getId() + " not found"));
            productMapped.setCategory(category);
            BeanUtils.copyProperties(productMapped, productDB);

            return productMapper.mapProductToProductDto(productRepository.save(productDB));
        }

        productMapped.setCategory(productDB.getCategory());
        BeanUtils.copyProperties(productMapped, productDB);

        return productMapper.mapProductToProductDto(productRepository.save(productDB));
    }

    @Override
    public Page<ProductDto> getProducts(int page, int size) {
        log.info("... invoking method ProduceServiceImpl.getProducts ...");

        PageRequest pr = PageRequest.of(page,size);
        return productRepository.findAll(pr)
                .map(productMapper::mapProductToProductDto);
    }

    @Override
    public Page<ProductDto> searchProducts(String productName, Long categoryId, Integer page, Integer size) {
        log.info("... invoking method ProduceServiceImpl.searchProducts ...");
        PageRequest pageRequest = PageRequest.of(page, size);

        if(categoryId != null) {
            Category category = categoryRepository
                    .findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with ID: " + categoryId + " not found"));

            return productRepository
                    .findByNameContainingIgnoreCaseAndCategoryId(productName, category.getId(), pageRequest)
                    .map(productMapper::mapProductToProductDto);
        }

        return productRepository.findByNameContainingIgnoreCase(productName, pageRequest)
                .map(productMapper::mapProductToProductDto);
    }
}
