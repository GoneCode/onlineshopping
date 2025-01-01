package com.onlineshopping.categoryservice.repository;

import com.onlineshopping.categoryservice.CategoryDTO;
import com.onlineshopping.categoryservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    boolean existsByName(String name);

    Category findByName(String name);
}
