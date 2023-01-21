package com.abs.restaurant.app.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryDto {

    private Long categoryId;

    private String name;

    private Boolean isActive;
}
