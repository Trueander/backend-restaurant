package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.dao.CategoryRepository;
import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import com.abs.restaurant.app.exceptions.ResourceNotFoundException;
import com.abs.restaurant.app.mapper.impl.CategoryMapper;
import com.abs.restaurant.app.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryRegistrationRequest categoryRequest) {
        log.info("... invoking method CategoryService.createCategory ...");

        Category category = categoryMapper.mapCategoryRegistrationRequestToCategory(categoryRequest);
        if(category == null) return null;

        return categoryMapper.mapCategoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public Optional<CategoryDto> findCategoryById(Long categoryId) {
        log.info("... invoking method CategoryService.findCategoryById ...");

        return categoryRepository.findById(categoryId)
                .map(categoryMapper::mapCategoryToCategoryDto);
    }

    @Override
    public CategoryDto updateCategory(CategoryUpdateRequest categoryRequest, Long categoryId) {
        log.info("... invoking method CategoryService.updateCategory ...");
        Category categoryDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Category with ID: " + categoryId + " not found"));

        Category categoryMapped = categoryMapper.mapCategoryUpdateRequestToCategory(categoryRequest);
        if(categoryMapped == null) return null;

        BeanUtils.copyProperties(categoryMapped, categoryDB);

        return categoryMapper
                .mapCategoryToCategoryDto(categoryRepository.save(categoryDB));
    }

    @Override
    public Page<CategoryDto> getCategories(int page, int size) {
        log.info("... invoking method CategoryService.getCategories ...");
        PageRequest pr = PageRequest.of(page,size);

        return categoryRepository.findAll(pr)
                .map(categoryMapper::mapCategoryToCategoryDto);
    }

    @Override
    public Page<CategoryDto> searchCategories(String categoryName, Integer page, Integer size) {
        log.info("... invoking method CategoryService.searchCategories ...");
        PageRequest pr = PageRequest.of(page,size);

        return categoryRepository.findByNameContainingIgnoreCase(categoryName, pr)
                .map(categoryMapper::mapCategoryToCategoryDto);
    }
}
