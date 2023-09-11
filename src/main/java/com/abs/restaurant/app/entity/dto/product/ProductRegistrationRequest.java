package com.abs.restaurant.app.entity.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductRegistrationRequest {

    @Size(min = 3, message = "name must have at least 3 characters")
    @NotEmpty(message = "name is required")
    private String name;

    @Size(min = 3, message = "description must have at least 3 characters")
    @NotEmpty(message = "description is required")
    private String description;

    @NotNull(message = "price is required")
    @PositiveOrZero(message = "price must be positive")
    private BigDecimal price;

    private String imageUrl;

    @NotNull(message = "stock is required")
    @PositiveOrZero(message = "stock must be positive")
    private Integer stock;

    @PositiveOrZero(message = "stock must be positive")
    private Double discount;

    @NotNull(message = "category is required")
    private Long categoryId;
}
