package com.onlineshopping.categoryservice.repository;

import com.onlineshopping.categoryservice.CategoryDTO;
import com.onlineshopping.categoryservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    boolean existsByName(String name);

    Category findByName(String name);
}
