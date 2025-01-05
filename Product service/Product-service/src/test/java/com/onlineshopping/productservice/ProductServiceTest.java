package com.onlineshopping.productservice;

import com.onlineshopping.productservice.DTO.ProductDTO;
import com.onlineshopping.productservice.client.CategoryClient;
import com.onlineshopping.productservice.entity.Product;
import com.onlineshopping.productservice.repository.ProductRepository;
import com.onlineshopping.productservice.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;



    @InjectMocks
    private ProductService productService;

    private Product product;

    private ModelMapper modelMapper;

    private ProductDTO productDTO;

    @Mock
    private CategoryClient categoryFeignClient;


}
