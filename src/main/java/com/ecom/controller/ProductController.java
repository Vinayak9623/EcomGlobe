package com.ecom.controller;

import com.ecom.dto.ProductDto;
import com.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;


    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {

        service.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductDto>> getAllProduct() {

        List<ProductDto> products = service.getAllProducts();

        return ResponseEntity.status(HttpStatus.OK).body(products);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        ProductDto product = service.updateProduct(productDto, id);
        return ResponseEntity.ok(product);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteproduct(@PathVariable Long id) {

        String s = service.deleteProduct(id);

        return ResponseEntity.ok(s);

    }
}
