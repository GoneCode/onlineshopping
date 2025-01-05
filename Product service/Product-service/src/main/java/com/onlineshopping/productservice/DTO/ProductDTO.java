package com.onlineshopping.productservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productId;
    private String name;
    private Double price;
    private String description;
    private Integer inventory;
    private String categoryId;
    private CategoryDTO category;
}
