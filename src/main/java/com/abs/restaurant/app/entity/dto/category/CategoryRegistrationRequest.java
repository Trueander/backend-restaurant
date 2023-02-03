package com.abs.restaurant.app.entity.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryRegistrationRequest {
    private String name;

    private String icon;
}
