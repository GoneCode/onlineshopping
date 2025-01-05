package com.onlineshopping.productservice;

import com.onlineshopping.productservice.DTO.ProductDTO;
import com.onlineshopping.productservice.entity.Product;
import com.onlineshopping.productservice.repository.ProductRepository;
import com.onlineshopping.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceApplicationTests {

	@MockBean
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	@Test
	void contextLoads() {
		// Mocking the service method to return a dummy ProductDTO
		ProductDTO mockProductDTO = new ProductDTO(); // Assuming ProductDTO is a valid class
		mockProductDTO.setProductId("1234");
		mockProductDTO.setName("Mock Product");

		when(productService.getProductById("1234")).thenReturn(mockProductDTO);

		// Verify behavior (optional, if needed for assertions)
		ProductDTO result = productService.getProductById("1234");
		assertNotNull(result);
		assertEquals("1234", result.getProductId());

		Product mockProduct = new Product();
		mockProduct.setId("1");
		mockProduct.setName("Test Product");
		mockProduct.setPrice(100.0);

		when(productRepository.findById("1")).thenReturn(Optional.of(mockProduct));

		Optional<Product> result1 = productRepository.findById("1");
		assertTrue(result1.isPresent());
		assertEquals("Test Product", result1.get().getName());


	}
}
