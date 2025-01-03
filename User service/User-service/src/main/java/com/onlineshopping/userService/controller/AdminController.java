package com.onlineshopping.userService.controller;




import com.onlineshopping.userService.client.CategoryClient;
import com.onlineshopping.userService.client.ProductClient;
import com.onlineshopping.userService.dto.CategoryDTO;
import com.onlineshopping.userService.dto.ProductDTO;
import com.onlineshopping.userService.dto.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/onlineShop")
public class AdminController {
    @Autowired
    ProductClient productClient ;

    @Autowired
    CategoryClient categoryClient ;

    @PostMapping("/product/addProducts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO productDTO){
        return ResponseEntity.ok(productClient.addProduct(productDTO));
    }

    @GetMapping("/product/allProducts")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(productClient.getAllProducts());
    }

    @GetMapping("/product/categoryId/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable String categoryId){
        List<ProductDTO> products = productClient.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/categoryName/{categoryName}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryName(@PathVariable String categoryName){
        List<ProductDTO> products = productClient.getProductsByCategoryName(categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/productId/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId) {
        ProductDTO products = productClient.getProductById(productId);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/product/updateProduct/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productId, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productClient.updateProduct(productId, productDTO);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/product/deleteProduct/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable String productId) {
        productClient.deleteProduct(productId);
        ResponseDTO response = new ResponseDTO(true, "Product deleted successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/getAllCategory")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryClient.getAllCategories());
    }

    @GetMapping("/category/categoryId/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok(categoryClient.getCategoryById(id));
    }

    @GetMapping("/category/categoryName/{name}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryClient.getCategoryByName(name));
    }

    @PostMapping("/category/addCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(201).body(categoryClient.createCategory(categoryDTO));
    }

    @PutMapping("/category/updateCategory/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryClient.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/category/deleteCategory/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteCategory(@PathVariable String id) {
        categoryClient.deleteCategory(id);
        ResponseDTO response = new ResponseDTO(true, "Category deleted successfully.");
        return ResponseEntity.ok(response);
    }
}
