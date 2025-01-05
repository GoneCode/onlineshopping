package com.onlineshopping.productservice.client;

import com.onlineshopping.productservice.DTO.CategoryClientFallback;
import com.onlineshopping.productservice.DTO.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", path = "/api/categories", fallback = CategoryClientFallback.class)
public interface CategoryClient {

    @GetMapping("Id/{categoryId}")
    CategoryDTO getCategoryById(@PathVariable("categoryId") String id);
    @GetMapping("name/{categoryName}")
    CategoryDTO getCategoryByName(@PathVariable("categoryName")String Name);
}
