package com.abs.restaurant.app.controller.impl;

import com.abs.restaurant.app.controller.ICategoryController;
import com.abs.restaurant.app.entity.dto.category.CategoryDto;
import com.abs.restaurant.app.entity.dto.category.CategoryRegistrationRequest;
import com.abs.restaurant.app.entity.dto.category.CategoryUpdateRequest;
import com.abs.restaurant.app.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/categories")
public class CategoryController implements ICategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    @Override
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRegistrationRequest categoryDto, BindingResult result) {

        CategoryDto createdCategory = categoryService.createCategory(categoryDto);

        if(createdCategory == null) return ResponseEntity
                .badRequest().body(Collections.singletonMap("message", "Fill mandatory fields."));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{category-id}")
                .buildAndExpand(createdCategory.getCategoryId())
                .toUri();

        return ResponseEntity.created(location).body(createdCategory);
    }

    @GetMapping("/{category-id}")
    @Override
    public ResponseEntity<?> findCategoryById(@PathVariable(name = "category-id") @NotNull Long categoryId) {

        Optional<CategoryDto> foundCategory = categoryService.findCategoryById(categoryId);

        if(foundCategory.isEmpty()) return new ResponseEntity<>
                (Collections.singletonMap("message","Category with ID: "+ categoryId + " not found."),
                        HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(foundCategory.get());
    }

    @PutMapping("/{category-id}")
    @Override
    public ResponseEntity<?> updateCategory(@PathVariable(name = "category-id") @NotNull Long categoryId,
                                            @Valid @RequestBody CategoryUpdateRequest categoryDto,
                                            BindingResult result) {

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);

        if(updatedCategory == null) return ResponseEntity
                .badRequest().body(Collections.singletonMap("message", "Fill mandatory fields."));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{category-id}")
                .buildAndExpand(updatedCategory.getCategoryId())
                .toUri();

        return ResponseEntity.created(location).body(updatedCategory);
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getCategoriesPageable(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "8")Integer size) {
        Page<CategoryDto> pageableCategories = categoryService.getCategoriesPageable(page, size);

        if(pageableCategories.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(pageableCategories);
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<?> getCategories() {
        List<CategoryDto> categories = categoryService.getCategories();

        if(categories.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<?> filterCategories(@RequestParam(name = "categoryName") String categoryName,
                                              @RequestParam(name = "page", defaultValue = "0") Integer page,
                                              @RequestParam(name = "size", defaultValue = "8") Integer size) {

        Page<CategoryDto> filteredCategories = categoryService.searchCategories(categoryName, page, size);

        if(filteredCategories.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(filteredCategories);
    }
}
