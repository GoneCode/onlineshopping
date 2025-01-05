package com.onlineshopping.categoryservice.service;

import com.onlineshopping.categoryservice.CategoryDTO;
import com.onlineshopping.categoryservice.entity.Category;
import com.onlineshopping.categoryservice.exception.CategoryAlreadyExistsException;
import com.onlineshopping.categoryservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @InjectMocks
//    private CategoryService categoryService;
//
//    private CategoryDTO categorydto;
//    private Category category;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @BeforeEach
//    void setUp() {
//        categorydto = new CategoryDTO("1011","YOGA MAT","1");
//        // Mock ModelMapper behavior
//        when(modelMapper.map(categorydto, Category.class)).thenReturn(category);
//
//
//    }
//
//    @Test
//    void getAllCategories() {
//    }
//
//    @Test
//    void getCategoryById() {
//    }
//
////    @Test
////    void createCategory() {
////        // Arrange
////        when(categoryRepository.save(any(Category.class))).thenReturn(category);
////        Category category = modelMapper.map(categorydto, Category.class);
////
////        category.setId(UUID.randomUUID().toString());
////        // Act
////        CategoryDTO addedCategory = categoryService.createCategory(new CategoryDTO("1","GAMES","games"));
////
////
////        // Assert
////        assertNotNull(addedCategory);
////        assertEquals("GAMES", addedCategory.getName());
////        verify(modelMapper, times(1)).map(categorydto, Category.class);
////        verify(categoryRepository, times(1)).save(any(Category.class));
////
////    }
//
//    @Test
//    void updateCategory() {
//    }
//
//    @Test
//    void deleteCategory() {
//    }
//
//    @Test
//    void getCategoryByName() {
//    }
}