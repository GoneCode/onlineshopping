package com.onlineshopping.productservice.repository;

import com.onlineshopping.productservice.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() throws Exception {
        product = new Product("10219", "IFB Microwave Oven", "Compact microwave with grill",8999.99,50,"1256");
        productRepository.save(product);
    }

    //positive scenario
    @Test
    void testFindByCategoryId_Found(){
        List<Product> productsList = productRepository.findByCategoryId("1256");
        assertThat(productsList.get(0).getCategoryId().equals(product.getCategoryId())).isTrue();
    }

    //negative scenario
    @Test
    void testFindByCategoryId_Not_Found(){
        List<Product> productsList = productRepository.findByCategoryId("1258");
        assertThat(productsList.isEmpty()).isTrue();
    }

    @AfterEach
    void tearDown() throws Exception {
        product = null;
        productRepository.deleteAll();
    }




}
