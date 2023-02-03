package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import com.abs.restaurant.app.mapper.ICategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements ICategoryMapper {

    @Override
    public Category mapCategoryRegistrationRequestToCategory(CategoryRegistrationRequest dtoIn) {
        if(dtoIn == null) return null;

        Category category = new Category();
        category.setName(dtoIn.getName());
        category.setIcon(dtoIn.getIcon());
        category.setIsActive(true);
        return category;
    }

    @Override
    public Category mapCategoryUpdateRequestToCategory(CategoryUpdateRequest dtoIn) {
        if(dtoIn == null) return null;

        Category category = new Category();
        category.setId(dtoIn.getCategoryId());
        category.setName(dtoIn.getName());
        category.setIcon(dtoIn.getIcon());
        category.setIsActive(dtoIn.getIsActive());
        return category;
    }

    @Override
    public CategoryDto mapCategoryToCategoryDto(Category category) {
        if(category == null) return null;

        CategoryDto dtoOut = new CategoryDto();
        dtoOut.setCategoryId(category.getId());
        dtoOut.setName(category.getName());
        dtoOut.setIcon(category.getIcon());
        dtoOut.setIsActive(category.getIsActive());
        return dtoOut;
    }
}
