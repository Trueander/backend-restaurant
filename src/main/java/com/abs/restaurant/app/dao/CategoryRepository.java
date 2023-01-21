package com.abs.restaurant.app.dao;

import com.abs.restaurant.app.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
