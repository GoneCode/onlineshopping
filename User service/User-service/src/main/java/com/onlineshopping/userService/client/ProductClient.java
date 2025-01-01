package com.onlineshopping.userService.client;



import com.onlineshopping.userService.dto.ProductDTO;
import com.onlineshopping.userService.dto.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", path = "/api/products")
public interface ProductClient {

    @GetMapping("/allproducts")
    List<ProductDTO> getAllProducts();

    @GetMapping("/categoryId/{categoryId}")
    List<ProductDTO> getProductsByCategoryId(@PathVariable("categoryId") String Name);

    @GetMapping("/categoryName/{categoryName}")
    List<ProductDTO> getProductsByCategoryName(@PathVariable("categoryName") String Name);

    @PutMapping("/{productId}")
    ProductDTO updateProduct(@PathVariable String productId, @RequestBody ProductDTO productDTO);

    @DeleteMapping("/{productId}")
    ResponseEntity<ResponseDTO> deleteProduct(@PathVariable String productId);

    @PostMapping
    ProductDTO addProduct(@RequestBody @Valid ProductDTO productDTO);

    @GetMapping("/productId/{productId}")
    ProductDTO getProductById(@PathVariable("productId")String Name);

}
