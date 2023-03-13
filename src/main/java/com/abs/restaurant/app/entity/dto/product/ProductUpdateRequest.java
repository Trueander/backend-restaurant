package com.abs.restaurant.app.entity.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
