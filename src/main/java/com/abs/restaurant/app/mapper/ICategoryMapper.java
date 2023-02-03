package com.abs.restaurant.app.mapper;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;

public interface ICategoryMapper {

    Category mapCategoryRegistrationRequestToCategory(CategoryRegistrationRequest dtoIn);

    Category mapCategoryUpdateRequestToCategory(CategoryUpdateRequest dtoIn);

    CategoryDto mapCategoryToCategoryDto(Category category);
}
