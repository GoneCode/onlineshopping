package com.onlineshopping.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
