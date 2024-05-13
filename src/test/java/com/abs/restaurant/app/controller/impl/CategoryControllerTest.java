package com.abs.restaurant.app.controller.impl;

import com.abs.restaurant.app.controller.CategoryController;
import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import com.abs.restaurant.app.mapper.impl.CategoryMapper;
import com.abs.restaurant.app.service.impl.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.abs.restaurant.app.util.EntityMock.getInstance;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private CategoryService categoryService;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    public void findCategoryByIdTest() throws Exception {
//        when(categoryService.findCategoryById(1L)).thenReturn(Optional.of(getInstance().getCategoryDto()));
//
//        mvc.perform(get("/api/categories/1").contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(APPLICATION_JSON))
//                .andExpect(jsonPath("$.categoryId").value(1L))
//                .andExpect(jsonPath("$.name").value("Main course"));
//
//        verify(categoryService).findCategoryById(1L);
//    }
//
//    @Test
//    public void findCategoryByIdWrongIdTest() throws Exception {
//
//        mvc.perform(get("/api/categories/1").contentType(APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Category with ID: 1 not found."));
//
//        verify(categoryService).findCategoryById(1L);
//    }
//
//    @Test
//    public void createCategoryTest() throws Exception {
//        CategoryRegistrationRequest input = getInstance().categoryRegistrationRequest();
//
//        when(categoryService.createCategory(any(CategoryRegistrationRequest.class)))
//                .thenReturn(getInstance().getCategoryDto());
//
//        mvc.perform(post("/api/categories")
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.categoryId").value(1L))
//                .andExpect(jsonPath("$.name").value("Main course"));
//
//        verify(categoryService).createCategory(any(CategoryRegistrationRequest.class));
//    }
//
//    @Test
//    public void updateCategoryTest() throws Exception {
//        CategoryUpdateRequest input = getInstance().categoryUpdateRequest();
//
//        when(categoryService.updateCategory(any(CategoryUpdateRequest.class), anyLong()))
//                .thenReturn(getInstance().getCategoryDto());
//
//        mvc.perform(put("/api/categories/1")
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.categoryId").value(1L))
//                .andExpect(jsonPath("$.name").value("Main course"));
//
//        verify(categoryService).updateCategory(any(CategoryUpdateRequest.class), anyLong());
//    }
//
//    @Test
//    public void getCategoriesPageableTest() throws Exception {
//        CategoryMapper mapper = new CategoryMapper();
//        Page<Category> input = getInstance().getPageableCategories();
//        when(categoryService.getCategoriesPageable(0,6)).thenReturn(input.map(mapper::mapCategoryToCategoryDto));
//
//        mvc.perform(get("/api/categories")
//                        .param("page","0")
//                        .param("size","6"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(1)));
//
//        verify(categoryService).getCategoriesPageable(0, 6);
//    }
//
//    @Test
//    public void getCategoriesPageableEmptyResultTest() throws Exception {
//        Page<CategoryDto> pageableCategorysEmpty = new PageImpl<>(new ArrayList<>());
//        when(categoryService.getCategoriesPageable(2,4)).thenReturn(pageableCategorysEmpty);
//
//        mvc.perform(get("/api/categories/")
//                        .param("page","2")
//                        .param("size","4"))
//                .andExpect(status().isNoContent());
//
//        verify(categoryService).getCategoriesPageable(2, 4);
//    }
//
//    @Test
//    public void getCategorysSearchTest() throws Exception {
//        CategoryDto category = getInstance().getCategoryDto();
//        List<CategoryDto> categorys = new ArrayList<>();
//        categorys.add(category);
//        Page<CategoryDto> pageableCategorys = new PageImpl<>(categorys);
//        when(categoryService.searchCategories("mix",0,6)).thenReturn(pageableCategorys);
//
//        mvc.perform(get("/api/categories/search")
//                        .param("categoryName", "mix")
//                        .param("page","0")
//                        .param("size","6"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(1)));
//
//        verify(categoryService).searchCategories("mix",0, 6);
//    }
//    @Test
//    public void getCategoriesSearchEmptyResultTest() throws Exception {
//        Page<CategoryDto> pageableCategorys = new PageImpl<>(new ArrayList<>());
//        when(categoryService.searchCategories("mix",0,6)).thenReturn(pageableCategorys);
//
//        mvc.perform(get("/api/categories/search")
//                        .param("categoryName", "mix")
//                        .param("page","0")
//                        .param("size","6"))
//                .andExpect(status().isNoContent());
//
//        verify(categoryService).searchCategories("mix",0, 6);
//    }
//
//    @Test
//    public void getCategoriesTest() throws Exception{
//        CategoryMapper mapper = new CategoryMapper();
//        List<Category> categories = getInstance().getPageableCategories().getContent();
//        when(categoryService.getCategories()).thenReturn(categories.stream()
//                .map(mapper::mapCategoryToCategoryDto).collect(Collectors.toList()));
//
//        mvc.perform(get("/api/categories/list"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//
//        verify(categoryService).getCategories();
//    }
//
//    @Test
//    public void getCategoriesEmptyResultTest() throws Exception{
//        when(categoryService.getCategories()).thenReturn(new ArrayList<>());
//
//        mvc.perform(get("/api/categories/list"))
//                .andExpect(status().isNoContent());
//
//        verify(categoryService).getCategories();
//    }

}