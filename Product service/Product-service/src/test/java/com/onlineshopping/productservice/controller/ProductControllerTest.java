package com.onlineshopping.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshopping.productservice.DTO.CategoryDTO;
import com.onlineshopping.productservice.controller.ProductController;
import com.onlineshopping.productservice.DTO.ProductDTO;
import com.onlineshopping.productservice.entity.Product;
import com.onlineshopping.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Mocking the ProductService, so Spring will inject this mock into the ProductController
    @MockBean
    private ProductService productService;

    @Mock
    private ProductController productController;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO("10219", "IFB Microwave Oven", 8999.99,"Compact microwave with grill", 50, "1256",new CategoryDTO());
        product = new Product("10219", "IFB Microwave Oven", "Compact microwave with grill",8999.99,50,"4585");

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

//    @Test
//    void testGetAllProducts() throws Exception {
//        when(productService.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
//
//        // Perform the GET request
//        MvcResult result = mockMvc.perform(get("/api/products/allproducts"))
//                .andExpect(status().isOk())  // Check for HTTP status 200
//                .andReturn();
//
//        // Print the response content type for debugging purposes
//        System.out.println("Response Content-Type: " + result.getResponse().getContentType());
//
//        // Assert the content type
//        mockMvc.perform(get("/api/products/allproducts"))
//                .andExpect(status().isOk())  // Assert the response status is OK (200)
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Assert content type is JSON
//                .andExpect(jsonPath("$[0].productId").value(productDTO.getProductId()))  // Assert the response contains expected fields
//                .andExpect(jsonPath("$[0].productName").value(productDTO.getName()));
//
//        // Verify that productService.getAllProducts() was called exactly once
//        verify(productService, times(1)).getAllProducts();
//    }
//
//    @Test
//    void testGetProductsByCategoryId() throws Exception {
//        List<ProductDTO> productList = Collections.singletonList(productDTO);
//
//        when(productService.getProductByCategoryId("1256")).thenReturn(productList);
//
//        mockMvc.perform(get("/api/products/categoryId/1256"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].categoryId").value("1256"))
//                .andExpect(jsonPath("$[0].productName").value(productDTO.getName()));
//
//        verify(productService, times(1)).getProductByCategoryId("1256");
//    }
//
//    @Test
//    void testGetProductById() throws Exception {
//        when(productService.getProductById("10219")).thenReturn(productDTO);
//
//        mockMvc.perform(get("/api/products/productId/10219"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.productId").value("10219"))
//                .andExpect(jsonPath("$.productName").value(productDTO.getName()));
//
//        verify(productService, times(1)).getProductById("10219");
//    }
//
//    @Test
//    void testAddProduct() throws Exception {
//        when(productService.addProduct(any(ProductDTO.class))).thenReturn(productDTO);
//
//        mockMvc.perform(post("/api/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(productDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.productId").value("10219"))
//                .andExpect(jsonPath("$.productName").value(productDTO.getName()));
//
//        verify(productService, times(1)).addProduct(any(ProductDTO.class));
//    }
//
//    @Test
//    void testUpdateProduct() throws Exception {
//        when(productService.updateProduct(eq("10219"), any(ProductDTO.class))).thenReturn(product);
//
//        mockMvc.perform(put("/api/products/10219")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(productDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.productId").value("10219"))
//                .andExpect(jsonPath("$.productName").value(productDTO.getName()));
//
//        verify(productService, times(1)).updateProduct(eq("10219"), any(ProductDTO.class));
//    }
//
//    @Test
//    void testDeleteProduct() throws Exception {
//        doNothing().when(productService).deleteProduct("10219");
//
//        mockMvc.perform(delete("/api/products/10219"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Product deleted successfully."));
//
//        verify(productService, times(1)).deleteProduct("10219");
//    }
//
//    // Utility method to convert objects to JSON string
//    private static String asJsonString(Object obj) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
