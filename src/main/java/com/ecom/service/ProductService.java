package com.ecom.service;

import com.ecom.dto.ProductDto;

import java.util.List;

public interface ProductService {

    public ProductDto addProduct(ProductDto productDto);
    public List<ProductDto> getAllProducts();
    public ProductDto updateProduct(ProductDto productDto, Long id);
    public String deleteProduct(Long id);
}
