package com.onlineshopping.userService.client;

import com.onlineshopping.userService.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "category-service", path = "/api/categories")
public interface CategoryClient {


    @GetMapping
     List<CategoryDTO> getAllCategories();

    @GetMapping("Id/{id}")
    CategoryDTO getCategoryById(@PathVariable String id);

    @GetMapping("name/{name}")
    CategoryDTO getCategoryByName(@PathVariable String name);

    @PostMapping
    CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO);

    @PutMapping("/{id}")
    CategoryDTO updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO);

    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable String id);





}
