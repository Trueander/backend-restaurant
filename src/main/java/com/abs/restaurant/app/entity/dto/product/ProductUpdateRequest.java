package com.abs.restaurant.app.entity.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private Long productId;

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;

    private Integer stock;

    private Boolean isActive;

    private Double discount;

    private Long categoryId;
}
