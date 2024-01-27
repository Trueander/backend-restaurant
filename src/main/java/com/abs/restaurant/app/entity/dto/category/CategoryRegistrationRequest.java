package com.abs.restaurant.app.entity.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class CategoryRegistrationRequest {

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "icon is required")
    private String icon;
}
