package com.onlineshopping.productservice.config;

import com.onlineshopping.productservice.DTO.ProductDTO;
import com.onlineshopping.productservice.entity.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ProductDTO, Product>() {
            @Override
            protected void configure() {
                map(source.getProductId(), destination.getId());  // Mapping productId to product's ID
                map(source.getCategoryId(), destination.getCategoryId());  // Mapping categoryId to product's categoryId


            }
        });
        return modelMapper;
    }

}
