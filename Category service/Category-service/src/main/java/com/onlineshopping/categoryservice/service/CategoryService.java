package com.onlineshopping.categoryservice.service;
import com.onlineshopping.categoryservice.CategoryDTO;
import com.onlineshopping.categoryservice.entity.Category;
import com.onlineshopping.categoryservice.exception.ResourceNotFoundException;
import com.onlineshopping.categoryservice.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for ID: " + id));
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new ResourceNotFoundException("Category name already exists");
        }
        Random rand = new Random();
        Category category = modelMapper.map(categoryDTO, Category.class);
        String id = String.format("%d", rand.nextInt(8999) + 1000);
        category.setId(id);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO updateCategory(String id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incorrect category name : " + categoryDTO.getName()));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if(category == null) {
            throw new ResourceNotFoundException("Incorrect category name : " + name);
        }
        return modelMapper.map(category, CategoryDTO.class);
    }
}

