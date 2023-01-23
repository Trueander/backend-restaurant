package com.abs.restaurant.app.controller.impl;

import com.abs.restaurant.app.controller.IProductController;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.entity.dto.ProductDto;
import com.abs.restaurant.app.mapper.IProductMapper;
import com.abs.restaurant.app.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/products")
@RestController
public class ProductController implements IProductController {

    private final IProductMapper productMapper;
    private final IProductService productService;


    @PostMapping
    @Override
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto, BindingResult result) {
        Product product = productMapper.mapProductDtoToProduct(productDto);

        if(product == null) return ResponseEntity.badRequest().build();

        Product createdProduct = productService.createProduct(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{product-id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();

        return ResponseEntity.created(location).body(productMapper.mapProductToProductDto(createdProduct));
    }

    @GetMapping("/{product-id}")
    @Override
    public ResponseEntity<?> findProductById(@PathVariable(name = "product-id") @NotNull Long productId) {

        Optional<Product> productDB = productService.findProductById(productId);

        if(!productDB.isPresent()) return new ResponseEntity<>("Product with ID: " + productId + " not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(productMapper.mapProductToProductDto(productDB.get()), HttpStatus.OK);
    }

    @PutMapping("/{product-id}")
    @Override
    public ResponseEntity<?> updateProduct(@PathVariable(name = "product-id") @NotNull Long productId, @RequestBody ProductDto productDto, BindingResult result) {

        Product updatedProduct = productService.updateProduct(productMapper.mapProductDtoToProduct(productDto), productId);

        if(updatedProduct == null) return ResponseEntity.badRequest().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{product-id}")
                .buildAndExpand(updatedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).body(productMapper.mapProductToProductDto(updatedProduct));
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getProducts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "8") Integer size) {

        if(page == null) page = 0;
        if(size == null) size = 0;

        Page<Product> pageableProducts = productService.getProducts(page, size);

        if(pageableProducts.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pageableProducts.map(productMapper::mapProductToProductDto));
    }
}
