package com.abs.restaurant.app.controller;

import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.mapper.IProductMapper;
import com.abs.restaurant.app.service.IProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final IProductService productService;
    private final IProductMapper productMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void createProduct(@Valid @RequestBody ProductRegistrationRequest productDto) {

        Product product = productMapper.mapProductRegistrationRequestToProduct(productDto);
        productService.createProduct(product);
    }

    @GetMapping("/{product-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findProductById(@PathVariable(name = "product-id") @NotNull Long productId) {

        Optional<Product> productDB = productService.findProductById(productId);

        if(productDB.isEmpty()) return new ResponseEntity<>
                (Collections.singletonMap("message", "Product with ID: " + productId + " not found."),
                        HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(productMapper.mapProductToProductDto(productDB.get()));
    }

    @PutMapping("/{product-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "product-id") @NotNull Long productId,
                                           @Valid @RequestBody ProductUpdateRequest productDto,
                                           BindingResult result) {

        Product product = productMapper.mapProductUpdateRequestToProduct(productDto);

        return ResponseEntity.ok(productMapper.mapProductToProductDto(productService.updateProduct(product, productId)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProductDto>> getProductsPageable(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "8") Integer size) {

        Page<ProductDto> pageableProducts = productService.getProducts(page, size).map(productMapper::mapProductToProductDto);

        if(pageableProducts.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(pageableProducts);
    }

    @GetMapping("/filter-by-name")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDto>> getProductsFilterByName(@RequestParam(name = "name") String productName) {
        return ResponseEntity.ok(productService.getProducts(productName)
                .stream()
                .map(productMapper::mapProductToProductDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProductDto>> filterProductsPageable(@RequestParam(name = "productName") String productName,
                                            @RequestParam(name = "categoryId", required = false) Long categoryId,
                                            @RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "8") Integer size) {

        Page<ProductDto> filteredProducts = productService.searchProducts(productName, categoryId, page, size)
                .map(productMapper::mapProductToProductDto);

        if(filteredProducts.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(filteredProducts);
    }

    @PutMapping("/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDto>> updateProductsStock(@RequestBody List<ProductUpdateRequest> productsDto) {
        return ResponseEntity.ok(productService.updateProductsStock(productsDto)
                .stream()
                .map(productMapper::mapProductToProductDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("/import")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDto>> importProductsFromExcel(@RequestParam("file") MultipartFile excelFile) {
        List<Product> productsFromExcel = productService.getProductsFromExcel(excelFile);

        return ResponseEntity.ok(productsFromExcel
                .stream()
                .map(productMapper::mapProductToProductDto)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public void createProducts(@Valid @RequestBody List<ProductRegistrationRequest> products) {
        List<Product> productsToRegister = products
                .stream()
                .map(productMapper::mapProductRegistrationRequestToProduct)
                .collect(Collectors.toList());

        productService.createProducts(productsToRegister);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
