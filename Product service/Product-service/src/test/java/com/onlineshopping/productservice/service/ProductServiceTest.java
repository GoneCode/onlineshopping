package com.onlineshopping.productservice.service;

import com.onlineshopping.productservice.DTO.CategoryDTO;
import com.onlineshopping.productservice.DTO.ProductDTO;
import com.onlineshopping.productservice.client.CategoryClient;
import com.onlineshopping.productservice.entity.Product;
import com.onlineshopping.productservice.exceptionhandler.ResourceNotFoundException;
import com.onlineshopping.productservice.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductService productService;
    @Mock
    AutoCloseable autoCloseable;
    @Mock
    Product product;
    @Mock
    ProductDTO productDTO;
    @Mock
    CategoryClient categoryClient;
    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository,modelMapper,categoryClient);
        product = new Product("10219", "IFB Microwave Oven", "Compact microwave with grill",8999.99,50,"4585");

        CategoryDTO categoryDTO = new CategoryDTO("1256","Home Appliances"," Appliances like refrigerators, microwaves");
        productDTO = modelMapper.map(product, ProductDTO.class);


    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllProducts_whenProductsExist_shouldReturnProductDTOList() {
        // Given: A list of products
        List<Product> productList = Arrays.asList(
                new Product("101", "Product A", "Description A", 100.0, 10, "123"),
                new Product("102", "Product B", "Description B", 200.0, 20, "124")
        );

        // Mock productRepository.findAll() to return the product list
        when(productRepository.findAll()).thenReturn(productList);

        // Mock ModelMapper to map Product to ProductDTO
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class)))
                .thenAnswer(invocation -> {
                    Product product = invocation.getArgument(0);
                    return new ProductDTO(product.getId(), product.getName(),  product.getPrice(), product.getDescription(), product.getInventory(), product.getCategoryId(),new CategoryDTO());
                });

        // When: Calling the getAllProducts method
        List<ProductDTO> productDTOList = productService.getAllProducts();

        // Then: Verify the response contains the correct number of products
        assertNotNull(productDTOList, "ProductDTO list should not be null");
        assertEquals(2, productDTOList.size(), "ProductDTO list size should match the number of products");

        // Verify individual product details
        assertEquals("101", productDTOList.get(0).getProductId(), "First product ID should match");
        assertEquals("Product A", productDTOList.get(0).getName(), "First product name should match");
        assertEquals("102", productDTOList.get(1).getProductId(), "Second product ID should match");
        assertEquals("Product B", productDTOList.get(1).getName(), "Second product name should match");

        // Verify that the product repository's findAll method was called
        verify(productRepository, times(1)).findAll();
    }

//    @Test
//    void getAllProducts_whenNoProductsExist_shouldThrowResourceNotFoundException() {
//        // Given: An empty product list
//        when(productRepository.findAll()).thenReturn(Collections.emptyList());
//
//        // When & Then: Verify that ResourceNotFoundException is thrown
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            productService.getAllProducts();
//        });
//
//        // Verify the exception message
//        assertEquals("", exception.getMessage(), "Exception message should match");
//
//        // Verify that the product repository's findAll method was called
//        verify(productRepository, times(1)).findAll();
//    }

    @Test
    void addProduct_whenCategoryIdIsNull_shouldThrowIllegalArgumentException() {
        // Given: ProductDTO with null category ID
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(null);

        // When & Then: verify that IllegalArgumentException is thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(productDTO);
        });

        // Verify the exception message
        assertEquals("Category ID is required.", exception.getMessage(), "Exception message should match");
    }

    @Test
    void addProduct_whenCategoryNotFound_shouldThrowResourceNotFoundException() {
        // Given: ProductDTO with a valid category ID
        String categoryId = "1256";
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(categoryId);

        // Mock the Feign client to return null for the given category ID (category not found)
        when(categoryClient.getCategoryById(categoryId)).thenReturn(null);

        // When & Then: verify that ResourceNotFoundException is thrown
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.addProduct(productDTO);
        });

        // Verify the exception message
        assertEquals("Category not found with ID: " + categoryId, exception.getMessage(), "Exception message should match");

        // Verify that the Feign client method was called
        verify(categoryClient, times(1)).getCategoryById(categoryId);
    }

    @Test
    void addProduct_whenProductIsSuccessfullyAdded() {
        // Given: ProductDTO with a valid category ID
        String categoryId = "1256";
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId("10219");
        productDTO.setCategoryId(categoryId);
        productDTO.setName("IFB Microwave Oven");
        productDTO.setDescription("Compact microwave with grill");
        productDTO.setPrice(8999.99);
        productDTO.setInventory(50);
        productDTO.setCategory(new CategoryDTO());

        CategoryDTO categoryDTO = new CategoryDTO("1256", "Home Appliances", "Appliances like refrigerators, microwaves");

        // Mock the Feign client to return the category details
        when(categoryClient.getCategoryById(categoryId)).thenReturn(categoryDTO);
        when(modelMapper.map(any(ProductDTO.class), eq(Product.class)))
                .thenAnswer(invocation -> {
                    ProductDTO dto = invocation.getArgument(0);
                    Product product = new Product();
                    product.setId("mocked-id");
                    product.setName(dto.getName());
                    product.setPrice(dto.getPrice());
                    product.setCategoryId(dto.getCategoryId());
                    product.setInventory(dto.getInventory());
                    return product;
                });
        // Mock the product repository to return a product with a generated ID
        Product savedProduct = new Product();
        savedProduct.setId("10219");
        savedProduct.setName("IFB Microwave Oven");
        savedProduct.setDescription("Compact microwave with grill");
        savedProduct.setPrice(8999.99);
        savedProduct.setInventory(50);
        savedProduct.setCategoryId(categoryId);

        // Mock productRepository.save() to simulate saving the product
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class)))
                .thenAnswer(invocation -> {
                    Product product = invocation.getArgument(0);
                    return new ProductDTO(product.getId(), product.getName(),  product.getPrice(), product.getDescription(), product.getInventory(), product.getCategoryId(),new CategoryDTO());
                });

        // When: calling the addProduct method
        ProductDTO savedProductDTO = productService.addProduct(productDTO);

        // Then: verify the saved product's details
        assertNotNull(savedProductDTO, "Saved ProductDTO should not be null");
        assertEquals("10219", savedProductDTO.getProductId(), "Product ID should match the saved ID");
        assertEquals("IFB Microwave Oven", savedProductDTO.getName(), "Product name should match");
        assertEquals("Home Appliances", savedProductDTO.getCategory().getName(), "Category name should match");

        // Verify that the category client was called once to fetch the category
        verify(categoryClient, times(1)).getCategoryById(categoryId);

        // Verify that the product repository save method was called once
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductByCategoryId_whenCategoryFound_shouldReturnProductDTOList() {
        // Mock data
        String categoryId = "4585";
        List<Product> products = List.of(
                new Product("10219", "Microwave", "Compact microwave with grill", 8999.99, 50, categoryId),
                new Product("10220", "Refrigerator", "Double door fridge", 19999.99, 30, categoryId)
        );

        CategoryDTO categoryDTO = new CategoryDTO(categoryId, "Home Appliances", "Appliances like refrigerators, microwaves");

        // Mocked ProductDTOs
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setProductId("10219");
        productDTO1.setName("Microwave");
        productDTO1.setCategoryId(categoryId);

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setProductId("10220");
        productDTO2.setName("Refrigerator");
        productDTO2.setCategoryId(categoryId);

        // Mock behavior
        when(productRepository.findByCategoryId(categoryId)).thenReturn(products);
        when(categoryClient.getCategoryById(categoryId)).thenReturn(categoryDTO);
        when(modelMapper.map(products.get(0), ProductDTO.class)).thenReturn(productDTO1);
        when(modelMapper.map(products.get(1), ProductDTO.class)).thenReturn(productDTO2);

        // Call the method
        List<ProductDTO> result = productService.getProductByCategoryId(categoryId);

        // Assertions
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Result size should match");
        assertEquals("10219", result.get(0).getProductId(), "First product ID should match");
        assertEquals("Microwave", result.get(0).getName(), "First product name should match");
        assertNotNull(result.get(0).getCategory(), "First product category should not be null");
        assertEquals("Home Appliances", result.get(0).getCategory().getName(), "First product category name should match");

        // Verify interactions
        verify(productRepository, times(1)).findByCategoryId(categoryId);
        verify(categoryClient, times(1)).getCategoryById(categoryId);
        verify(modelMapper, times(1)).map(products.get(0), ProductDTO.class);
        verify(modelMapper, times(1)).map(products.get(1), ProductDTO.class);
    }

    @Test
    void getProductByCategoryId_whenNoProductsFound() {
        // Given: valid category ID but no products found
        String categoryId = "1256";

        // Mock the repository to return an empty list for the category ID
        when(productRepository.findByCategoryId(categoryId)).thenReturn(Collections.emptyList());

        // When & Then: verify that the ResourceNotFoundException is thrown
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductByCategoryId(categoryId);
        });

        // Verify the exception message
        assertEquals("No products found for category ID: " + categoryId, exception.getMessage(), "Exception message should match");

        // Verify that the category client was not called, as no products were found
        verify(productRepository, times(1)).findByCategoryId(categoryId);
        verify(categoryClient, times(0)).getCategoryById(categoryId);
    }



    @Test
    void getProductByCategoryName_whenCategoryFound() {
        // Given: a valid category name and mock responses
        String categoryName = "Home Appliances";
        CategoryDTO category = new CategoryDTO("1256", "Home Appliances", "Appliances like refrigerators, microwaves");
        Product product1 = new Product("10219", "IFB Microwave Oven", "Compact microwave with grill", 8999.99, 50, "1256");
        Product product2 = new Product("10220", "Samsung Refrigerator", "Large double-door refrigerator", 35000.00, 30, "1256");

        // Mock the Feign client to return the category based on name
        when(categoryClient.getCategoryByName(categoryName)).thenReturn(category);

        // Mock the repository to return a list of products for the category ID
        when(productRepository.findByCategoryId("1256")).thenReturn(Arrays.asList(product1, product2));
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class)))
                .thenAnswer(invocation -> {
                    Product product = invocation.getArgument(0);
                    return new ProductDTO(product.getId(), product.getName(),  product.getPrice(), product.getDescription(), product.getInventory(), product.getCategoryId(),new CategoryDTO());
                });
        // When: calling the service method to get products by category name
        List<ProductDTO> products = productService.getProductByCategoryName(categoryName);

        // Then: verify the results
        assertNotNull(products, "Product list should not be null");
        assertEquals(2, products.size(), "There should be two products in the list");
        assertEquals("10219", products.get(0).getProductId(), "First product ID should match");
        assertEquals("10220", products.get(1).getProductId(), "Second product ID should match");

        // Verify the interactions with mocks
        verify(categoryClient, times(1)).getCategoryByName(categoryName);
        verify(productRepository, times(1)).findByCategoryId("1256");
    }

    @Test
    void getProductByCategoryName_whenCategoryNotFound() {
        // Given: a non-existent category name
        String categoryName = "Non Existent Category";

        // Mock the Feign client to return null (or simulate exception behavior for a non-existent category)
        when(categoryClient.getCategoryByName(categoryName)).thenReturn(null);  // Or throw exception if needed

        // When & Then: verify that the ResourceNotFoundException is thrown
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductByCategoryName(categoryName);
        });

        // Verify the exception message
        assertEquals("Incorrect category name : Non Existent Category", exception.getMessage(), "Exception message should match");

        // Verify that the product repository was not called (since category was not found)
        verify(categoryClient, times(1)).getCategoryByName(categoryName);
        verify(productRepository, times(0)).findByCategoryId(anyString());  // No product search should be made
    }


    @Test
    void deleteProductById_whenProductExists() {
        // Given: a product exists in the repository
        Product product = new Product("10219", "IFB Microwave Oven", "Compact microwave with grill", 8999.99, 50, "4585");
        when(productRepository.findById("10219")).thenReturn(Optional.of(product));

        // When: calling the delete method
        productService.deleteProduct("10219");

        // Then: verify that delete was called on the repository
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProductById_whenProductNotFound() {
        // Given: no product exists with the provided ID
        when(productRepository.findById("10219")).thenReturn(Optional.empty());

        // When & Then: verify that the ResourceNotFoundException is thrown
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct("10219");
        });

        // Verify the exception message
        assertEquals("Product not found with ID: 10219", exception.getMessage(), "Exception message should match");

        // Verify that delete was not called
        verify(productRepository, times(0)).delete(any(Product.class));
    }



    @Test
    void getProductById_whenProductExists_shouldReturnProductDTO() {
        // Given
        String productId = "10219";
        Product product = new Product(productId, "IFB Microwave Oven", "Compact microwave with grill", 8999.99, 50, "4585");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productId); // Ensure ProductDTO has the correct ID
        productDTO.setName("IFB Microwave Oven");
        productDTO.setDescription("Compact microwave with grill");
        productDTO.setPrice(8999.99);
        productDTO.setInventory(50);
        productDTO.setCategoryId("4585");

        CategoryDTO categoryDTO = new CategoryDTO("4585", "Home Appliances", "Appliances like refrigerators, microwaves");

        // Mock behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
        when(categoryClient.getCategoryById("4585")).thenReturn(categoryDTO);

        // When
        ProductDTO result = productService.getProductById(productId);

        // Then
        assertNotNull(result, "ProductDTO should not be null");
        assertEquals(productId, result.getProductId(), "Product ID should match");
        assertEquals("IFB Microwave Oven", result.getName(), "Product name should match");
        assertNotNull(result.getCategory(), "Category should not be null");
        assertEquals("Home Appliances", result.getCategory().getName(), "Category name should match");

        // Verify interactions
        verify(productRepository, times(1)).findById(productId);
        verify(modelMapper, times(1)).map(product, ProductDTO.class);
        verify(categoryClient, times(1)).getCategoryById("4585");
    }

    @Test
    void getProductById_whenProductNotFound() {
        // Given: No product exists with this ID
        when(productRepository.findById("10219")).thenReturn(Optional.empty());

        // When & Then: verify that the ResourceNotFoundException is thrown
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById("10219");
        });

        // Verify the exception message
        assertEquals("Product not found with ID: 10219", exception.getMessage(), "Exception message should match");

        // Verify the repository interaction
        verify(productRepository, times(1)).findById("10219");
        verify(categoryClient, times(0)).getCategoryById(anyString());
    }
}