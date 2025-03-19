package com.ecom.controller;

import com.ecom.dto.CategoryDto;
import com.ecom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {

        categoryService.createCatogry(categoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<CategoryDto>> getCategory() {

        List<CategoryDto> categories = categoryService.getCategories();

        return ResponseEntity.status(HttpStatus.OK).body(categories);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto, @PathVariable long id) {


        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, id);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDto1);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {

        String category = categoryService.deleteCategory(id);
        return ResponseEntity.ok(category);
    }
}
