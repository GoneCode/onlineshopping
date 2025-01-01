package com.onlineshopping.userService.controller;


import com.onlineshopping.userService.client.ProductClient;
import com.onlineshopping.userService.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    ProductClient productClient ;

    @PostMapping("/allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProductsForUser(){
        return ResponseEntity.ok(productClient.getAllProducts());
    }

}
