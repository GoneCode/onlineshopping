package com.onlineshopping.productservice.DTO;

import com.onlineshopping.productservice.client.CategoryClient;
import org.springframework.stereotype.Component;

@Component
public class CategoryClientFallback implements CategoryClient {

    @Override
    public CategoryDTO getCategoryById(String id) {
        return null;
    }

    @Override
    public CategoryDTO getCategoryByName(String Name) {
        return new CategoryDTO("404", "Unknown Category", "Fallback description");
    }
}
