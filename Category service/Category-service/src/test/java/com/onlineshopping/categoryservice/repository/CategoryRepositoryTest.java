package com.onlineshopping.categoryservice.repository;

import com.onlineshopping.categoryservice.CategoryDTO;
import com.onlineshopping.categoryservice.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {


    @Mock
    private CategoryRepository categoryRepository;



//    @Test
//    public void categoryRepository_saveAll_ReturnSaveCategory(){
//
//        //arrange
//        Category category = Category.builder().
//                id("1234")
//                .name("GAMES")
//                .description("games for ps5").build();
//
//        //act
//        Category saveCategory = categoryRepository.save(category);
//
//        //assert
//        //assertNotNull(saveCategory);
//        verify(categoryRepository, times(1)).save(any(Category.class));
//        assertEquals("GAMES", saveCategory.getName());
//    }




}