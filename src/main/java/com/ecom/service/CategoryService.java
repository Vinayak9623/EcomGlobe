package com.ecom.service;

import com.ecom.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCatogry(CategoryDto categoryDto);
    public List<CategoryDto> getCategories();
    public CategoryDto updateCategory(CategoryDto categoryDto,Long id);
    public String deleteCategory(long id);
}
