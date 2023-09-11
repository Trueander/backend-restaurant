package com.abs.restaurant.app.entity.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class CategoryUpdateRequest {
    private Long categoryId;

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "icon is required")
    private String icon;

    @NotNull(message = "isActive is required")
    private Boolean isActive;
}
