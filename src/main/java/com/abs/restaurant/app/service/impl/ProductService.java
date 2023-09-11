package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.dao.CategoryRepository;
import com.abs.restaurant.app.dao.ProductRepository;
import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.exceptions.ResourceNotFoundException;
import com.abs.restaurant.app.mapper.IExcelToProductMapper;
import com.abs.restaurant.app.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private static final int EXCEL_TITLES_ROW = 0;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final IExcelToProductMapper excelToProductMapper;

    @Transactional
    @Override
    public void createProduct(Product product) {
        log.info("... invoking method ProduceServiceImpl.createProduct ...");

        if(product.getCategory().getId() != null) {
            Category category = categoryRepository
                    .findById(product.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException
                            ("Category with ID: " + product.getCategory().getId() + " not found"));
            product.setCategory(category);
        }

        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findProductById(Long productId) {
        log.info("... invoking method ProduceServiceImpl.findProductById ...");
        return productRepository.findById(productId);
    }

    @Transactional
    @Override
    public Product updateProduct(Product product, Long productId) {
        log.info("... invoking method ProduceServiceImpl.updateProduct ...");
        Product productDB = productRepository
                .findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + productId + " not found"));

        if(productDB.getCategory() != null &&
                productDB.getCategory().getId() != null &&
                !product.getCategory().getId().equals(productDB.getCategory().getId())) {

            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException
                            ("Category with ID: " + product.getCategory().getId() + " not found"));

            product.setCategory(category);
        } else {
            product.setCategory(productDB.getCategory());
        }
        BeanUtils.copyProperties(product, productDB);
        return productRepository.save(productDB);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getProducts(int page, int size) {
        log.info("... invoking method ProduceServiceImpl.getProducts ...");

        PageRequest pr = PageRequest.of(page,size);
        return productRepository.findAll(pr);
    }

    @Override
    public List<Product> getProducts(String productName) {
        return productRepository
                .findByNameContainingIgnoreCase(productName);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> searchProducts(String productName, Long categoryId, Integer page, Integer size) {
        log.info("... invoking method ProduceServiceImpl.searchProducts ...");
        PageRequest pageRequest = PageRequest.of(page, size);

        if(categoryId != null) {
            Category category = categoryRepository
                    .findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with ID: " + categoryId + " not found"));

            return productRepository
                    .findByNameContainingIgnoreCaseAndCategoryId(productName, category.getId(), pageRequest);
        }

        return productRepository.findByNameContainingIgnoreCase(productName, pageRequest);
    }

    @Transactional
    @Override
    public List<Product> updateProductsStock(List<ProductUpdateRequest> productsDto) {
        log.info("... invoking method ProduceServiceImpl.updateProductsStock ...");

        List<Long> productsIds = productsDto.stream()
                .map(ProductUpdateRequest::getProductId).collect(Collectors.toList());

        List<Product> productsDB = (List<Product>) productRepository.findAllById(productsIds);
        productsDto.forEach(prodDto -> {
            productsDB.stream()
                    .filter(p -> p.getId().equals(prodDto.getProductId()))
                    .findFirst()
                    .ifPresent(p -> p.setStock(prodDto.getStock()));
        });

        return (List<Product>) productRepository.saveAll(productsDB);

    }

    @Override
    public List<Product> getProductsFromExcel(MultipartFile excelProductsData) {

        List<Product> productsFromExcel = new ArrayList<>();
        try{
            InputStream excelStream = excelProductsData.getInputStream();
            Workbook workbook = new XSSFWorkbook(excelStream);
            Sheet sheet = workbook.getSheetAt(EXCEL_TITLES_ROW);
            Iterator<Row> rowIterator = sheet.iterator();


            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                productsFromExcel.add(excelToProductMapper.mapFromWorkBookToProduct(row));
            }
            excelStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return productsFromExcel;
    }

    @Override
    public void createProducts(List<Product> products) {
        productRepository.saveAll(products);
    }
}
