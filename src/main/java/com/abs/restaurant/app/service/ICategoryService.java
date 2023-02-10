package com.abs.restaurant.app.service;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    CategoryDto createCategory(CategoryRegistrationRequest categoryRequest);
    Optional<CategoryDto> findCategoryById(Long categoryId);
    CategoryDto updateCategory(CategoryUpdateRequest categoryRequest, Long categoryId);

    Page<CategoryDto> getCategoriesPageable(int page, int size);

    List<CategoryDto> getCategories();

    Page<CategoryDto> searchCategories(String categoryName, Integer page, Integer size);
}
