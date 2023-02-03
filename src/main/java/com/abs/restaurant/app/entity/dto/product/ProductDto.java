package com.abs.restaurant.app.entity.dto.product;

import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductDto {

    private Long productId;

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;

    private Integer stock;

    private Boolean isActive;

    private Double discount;

    private CategoryDto category;
}
