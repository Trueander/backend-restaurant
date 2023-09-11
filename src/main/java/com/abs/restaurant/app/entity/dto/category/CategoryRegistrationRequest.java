package com.abs.restaurant.app.entity.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
public class CategoryRegistrationRequest {

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "icon is required")
    private String icon;
}
