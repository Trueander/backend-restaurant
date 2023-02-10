package com.abs.restaurant.app.service.impl;

import com.abs.restaurant.app.dao.CategoryRepository;
import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import com.abs.restaurant.app.exceptions.ResourceNotFoundException;
import com.abs.restaurant.app.mapper.impl.CategoryMapper;
import com.abs.restaurant.app.service.ICategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.abs.restaurant.app.util.EntityMock.getInstance;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private ICategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper = new CategoryMapper();

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, categoryMapper);
    }

    @Test
    void createCategoryTest() throws IOException {
        when(categoryRepository.save(any(Category.class)))
                .thenReturn(getInstance().getCategory());

        CategoryRegistrationRequest input = getInstance().categoryRegistrationRequest();
        CategoryDto result = categoryService.createCategory(input);

        assertNotNull(result);
        assertNotNull(result.getCategoryId());
        assertNotNull(result.getName());
        assertNotNull(result.getIcon());
        assertNotNull(result.getIsActive());

        assertEquals(1L, result.getCategoryId());
        assertEquals(input.getName(), result.getName());
        assertEquals(input.getIcon(), result.getIcon());
        assertEquals(true, result.getIsActive());
    }

    @Test
    void createCategoryNullTest() {
        CategoryDto result = categoryService.createCategory(null);

        assertNull(result);

        verify(categoryRepository, never()).save(any());
    }

    @Test
    void getCategoryByIdTest() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(getInstance().getCategory()));

        Optional<CategoryDto> result = categoryService.findCategoryById(1L);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertNotNull(result.get());
        assertNotNull(result.get().getCategoryId());
        assertNotNull(result.get().getName());
        assertNotNull(result.get().getIsActive());
        assertNotNull(result.get().getIcon());

        verify(categoryRepository).findById(1L);
    }

    @Test
    void getCategoryByIdNotFoundTest() {
        Optional<CategoryDto> result = categoryService.findCategoryById(2L);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(categoryRepository).findById(2L);
    }

    @Test
    void updateCategoryTest() throws IOException {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(getInstance().getCategory()));
        when(categoryRepository.save(any(Category.class))).thenReturn(getInstance().getCategory());

        CategoryUpdateRequest input = getInstance().categoryUpdateRequest();
        CategoryDto result = categoryService.updateCategory(input, 1L);

        assertNotNull(result);
        assertNotNull(result.getCategoryId());
        assertNotNull(result.getName());
        assertNotNull(result.getIcon());
        assertNotNull(result.getIsActive());

        assertEquals(1L, result.getCategoryId());
        assertEquals(input.getName(), result.getName());
        assertEquals(input.getIcon(), result.getIcon());
        assertEquals(true, result.getIsActive());

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void updateCategoryWithIdNotFoundTest() throws IOException {

        CategoryUpdateRequest input = getInstance().categoryUpdateRequest();

        ResourceNotFoundException errorMessage = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(input, 1L);
        });

        assertEquals("Category with ID: 1 not found", errorMessage.getMessage());


        verify(categoryRepository).findById(1L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategoryNullTest() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(getInstance().getCategory()));

        CategoryDto result = categoryService.updateCategory(null, 1L);

        assertNull(result);

        verify(categoryRepository).findById(1L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    public void pageableCategoriesTest() throws IOException {
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(getInstance().getPageableCategories());

        Page<CategoryDto> categories = categoryService.getCategoriesPageable(1, 5);

        assertNotNull(categories);
        assertNotNull(categories.getContent());

        verify(categoryRepository).findAll(any(PageRequest.class));
    }

    @Test
    public void getCategoriesTest() throws IOException {
        when(categoryRepository.findAll())
                .thenReturn(getInstance().getPageableCategories().getContent());

        List<CategoryDto> categories = categoryService.getCategories();

        assertNotNull(categories);
        assertNotNull(categories.get(0));
        assertNotNull(categories.get(0).getCategoryId());
        assertNotNull(categories.get(0).getName());
        assertNotNull(categories.get(0).getIsActive());
        assertNotNull(categories.get(0).getIcon());

        verify(categoryRepository).findAll();
    }

    @Test
    public void searchCategoriesTest() {
        when(categoryRepository.findByNameContainingIgnoreCase(anyString() ,any(PageRequest.class))).thenReturn(getInstance().getPageableCategories());

        Page<CategoryDto> categories = categoryService.searchCategories("main",1, 5);

        assertNotNull(categories);
        assertNotNull(categories.getContent());

        verify(categoryRepository).findByNameContainingIgnoreCase(anyString() ,any(PageRequest.class));
    }
}