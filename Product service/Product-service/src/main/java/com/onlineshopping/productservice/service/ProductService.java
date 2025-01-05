package com.onlineshopping.productservice.service;

import com.onlineshopping.productservice.DTO.CategoryDTO;
import com.onlineshopping.productservice.DTO.ProductDTO;
import com.onlineshopping.productservice.client.CategoryClient;
import com.onlineshopping.productservice.entity.Product;
import com.onlineshopping.productservice.exceptionhandler.ResourceNotFoundException;
import com.onlineshopping.productservice.repository.ProductRepository;
import jakarta.ws.rs.ServiceUnavailableException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryClient categoryClient;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CategoryClient categoryClient) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryClient = categoryClient;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            if (product.getCategoryId() != null) {
                try {
                    // Fetch category details from Category Service
                    CategoryDTO categoryDTO = categoryClient.getCategoryById(product.getCategoryId());
                    productDTO.setCategory(categoryDTO);
                } catch (Exception e) {
                    // Handle service unavailability or category not found
                    productDTO.setCategory(new CategoryDTO(product.getCategoryId(), null, null));
                }
            }
            return productDTO;
        }).collect(Collectors.toList());
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        if (productDTO.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID is required.");
        }

        CategoryDTO category = categoryClient.getCategoryById(productDTO.getCategoryId());
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with ID: " + productDTO.getCategoryId());
        }

        // Map and save product
        Product product = modelMapper.map(productDTO, Product.class);
        Random rand = new Random();
        String id = String.format("%d", rand.nextInt(8999) + 10000);
        product.setId(id);
        product = productRepository.save(product);

        // Map to ProductDTO for response
        ProductDTO savedProductDTO = modelMapper.map(product, ProductDTO.class);
        savedProductDTO.setCategory(category); // Populate category details for response
        return savedProductDTO;
    }

    public List<ProductDTO> getProductByCategoryId(String categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category ID: " + categoryId);
        }

        // Fetch category details from the Category Service
        CategoryDTO categoryDTO;
        try {
            categoryDTO = categoryClient.getCategoryById(categoryId);
        } catch (Exception e) {
            //log.error("Failed to fetch category details for ID: {}", categoryId, e);
            throw new ServiceUnavailableException("Category service is unavailable");
        }

        // Map products and set category details
        return products.stream().map(product -> {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setCategory(categoryDTO); // Set category details in the response
            return productDTO;
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategoryName(String categoryName) {
        CategoryDTO categoryDTO;
        categoryDTO = categoryClient.getCategoryByName(categoryName);
        if (categoryDTO==null) {
            throw new ResourceNotFoundException("Incorrect category name : " + categoryName);
        }
        String categoryId = categoryDTO.getId();
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category name: " + categoryName);
        }
        return products.stream().map(product -> {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setCategory(categoryDTO); // Set category details in the response
            return productDTO;
        }).collect(Collectors.toList());
    }

    public Product updateProduct(String productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getInventory() != null) {
            product.setInventory(productDTO.getInventory());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }

        return productRepository.save(product);
    }

    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        productRepository.delete(product);
    }

    public ProductDTO getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        CategoryDTO categoryDTO;
        try {
            categoryDTO = categoryClient.getCategoryById(product.getCategoryId());
        } catch (Exception e) {
            //log.error("Failed to fetch category details for ID: {}", categoryId, e);
            throw new ServiceUnavailableException("Category service is unavailable");
        }
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setCategory(categoryDTO); // Set category details in the response
        return productDTO;
    }



}
