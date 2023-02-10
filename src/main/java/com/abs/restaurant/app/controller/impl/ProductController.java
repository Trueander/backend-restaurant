package com.abs.restaurant.app.controller.impl;

import com.abs.restaurant.app.controller.IProductController;
import com.abs.restaurant.app.entity.dto.product.ProductDto;
import com.abs.restaurant.app.entity.dto.product.ProductRegistrationRequest;
import com.abs.restaurant.app.entity.dto.product.ProductUpdateRequest;
import com.abs.restaurant.app.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/products")
@RestController
public class ProductController implements IProductController {

    private final IProductService productService;

    @PostMapping
    @Override
    public ResponseEntity<?> createProduct(@RequestBody ProductRegistrationRequest productDto, BindingResult result) {

        ProductDto createdProduct = productService.createProduct(productDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{product-id}")
                .buildAndExpand(createdProduct.getProductId())
                .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }

    @GetMapping("/{product-id}")
    @Override
    public ResponseEntity<?> findProductById(@PathVariable(name = "product-id") @NotNull Long productId) {

        Optional<ProductDto> productDB = productService.findProductById(productId);

        if(productDB.isEmpty()) return new ResponseEntity<>
                (Collections.singletonMap("message", "Product with ID: " + productId + " not found."),
                        HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(productDB.get());
    }

    @PutMapping("/{product-id}")
    @Override
    public ResponseEntity<?> updateProduct(@PathVariable(name = "product-id") @NotNull Long productId,
                                           @Valid @RequestBody ProductUpdateRequest productDto,
                                           BindingResult result) {

        ProductDto updatedProduct = productService.updateProduct(productDto, productId);

        if(updatedProduct == null) return ResponseEntity.badRequest().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{product-id}")
                .buildAndExpand(updatedProduct.getProductId())
                .toUri();

        return ResponseEntity.created(location).body(updatedProduct);
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getProducts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "8") Integer size) {

        Page<ProductDto> pageableProducts = productService.getProducts(page, size);

        if(pageableProducts.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(pageableProducts);
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<?> filterProducts(@RequestParam(name = "productName") String productName,
                                            @RequestParam(name = "categoryId", required = false) Long categoryId,
                                            @RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "8") Integer size) {

        Page<ProductDto> filteredProducts = productService.searchProducts(productName, categoryId, page, size);

        if(filteredProducts.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(filteredProducts);
    }
}
