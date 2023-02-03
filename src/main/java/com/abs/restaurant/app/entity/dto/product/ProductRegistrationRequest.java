package com.abs.restaurant.app.entity.dto.product;

import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductRegistrationRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;

    private Integer stock;

    private Double discount;

    private Long categoryId;
}
