package com.abs.restaurant.app.entity.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryUpdateRequest {
    private Long categoryId;

    private String name;

    private String icon;

    private Boolean isActive;
}
