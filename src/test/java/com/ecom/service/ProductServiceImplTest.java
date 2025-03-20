package com.ecom.service;
import com.ecom.Repository.ProductRepository;
import com.ecom.customException.ProductNotFoundException;
import com.ecom.dto.ProductDto;
import com.ecom.entity.Product;
import com.ecom.serviceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper productMapper;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "Laptop", "High-end Laptop", 1000.0, null);
        productDto = new ProductDto(1L, "Laptop", "High-end Laptop", 1000.0, null);
    }

    @Test
    void addProduct() {
        when(productMapper.map(productDto, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.map(product, ProductDto.class)).thenReturn(productDto);

        ProductDto result = productService.addProduct(productDto);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals("High-end Laptop", result.getDescription());
        assertEquals(1000.0, result.getPrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getAllProducts() {

        Product product2=new Product(2L, "Second Laptop", "High-end Laptop with upudated version", 2000.0, null);
        ProductDto productDto2=new ProductDto(2L, "Second Laptop", "High-end Laptop with upudated version", 2000.0, null);

        when(productRepository.findAll()).thenReturn(List.of(product,product2));
        when(productMapper.map(product,ProductDto.class)).thenReturn(productDto);
        when(productMapper.map(product2,ProductDto.class)).thenReturn(productDto2);


        List<ProductDto>  productDtos=productService.getAllProducts();

        assertEquals(2,productDtos.size());
        assertEquals("Laptop",productDtos.get(0).getName());
        verify(productRepository,times(1)).findAll();

    }

    @Test
    void updateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.map(productDto, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.map(product, ProductDto.class)).thenReturn(productDto);

        ProductDto result = productService.updateProduct(productDto, 1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void deleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        doNothing().when(productRepository).delete(product);
        String result = productService.deleteProduct(1L);

        assertEquals("Product with ID 1 deleted successfully", result);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProductNotFound() {
        when(productRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(55L));
    }
}

