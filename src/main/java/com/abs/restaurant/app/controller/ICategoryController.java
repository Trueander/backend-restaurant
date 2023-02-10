package com.abs.restaurant.app.controller;

import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;

public interface ICategoryController {

    ResponseEntity<?> createCategory(CategoryRegistrationRequest categoryDto, BindingResult result);

    ResponseEntity<?> findCategoryById(@NotNull Long categoryId);

    ResponseEntity<?> updateCategory(@NotNull Long categoryId, CategoryUpdateRequest categoryDto, BindingResult result);

    ResponseEntity<?> getCategoriesPageable(Integer page, Integer size);

    ResponseEntity<?> getCategories();

    ResponseEntity<?> filterCategories(String categoryName, Integer page, Integer size);

}
