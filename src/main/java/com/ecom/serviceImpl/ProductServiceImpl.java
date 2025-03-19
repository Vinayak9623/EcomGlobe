package com.ecom.serviceImpl;

import com.ecom.Repository.CategoryRepository;
import com.ecom.Repository.ProductRepository;
import com.ecom.customException.CategoryNotFoundException;
import com.ecom.customException.ProductNotFoundException;
import com.ecom.dto.ProductDto;
import com.ecom.entity.Category;
import com.ecom.entity.Product;
import com.ecom.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper productmapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) {

        Product product = productmapper.map(productDto, Product.class);
        productRepository.save(product);
        return productmapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts() {

        List<Product> product = productRepository.findAll();
        return product.stream().map(p -> productmapper
                .map(p, ProductDto.class)).collect(Collectors.toList());

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {

        //productmapper.map(productDto, Product.class);
        Product existProduct = productRepository
                .findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        existProduct.setName(productDto.getName());
        existProduct.setDescription(productDto.getDescription());
        existProduct.setPrice(productDto.getPrice());
        productRepository.save(existProduct);

        return productmapper.map(existProduct, ProductDto.class);
    }



    @Override
    public String deleteProduct(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not found"));

        productRepository.delete(product);
        return "Product with ID " + id + " deleted successfully";
    }



}
