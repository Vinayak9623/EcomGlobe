package com.ecom.serviceImpl;

import com.ecom.Repository.CategoryRepository;
import com.ecom.customException.CategoryNotFoundException;
import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper categoryMapper;

    @Override
    public CategoryDto createCatogry(CategoryDto categoryDto) {

        Category category=categoryMapper.map(categoryDto, Category.class);

        categoryRepository.save(category);

        return categoryMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {

        List<Category> categories=categoryRepository.findAll();

        return categories.stream()
                .map(category->categoryMapper
                        .map(category, CategoryDto.class)).collect(Collectors.toList());

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {

        categoryMapper.map(categoryDto,Category.class);
        Category category=categoryRepository
                .findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return categoryMapper.map(category,CategoryDto.class);
    }

    @Override
    public String deleteCategory(long id) {

        Category category=categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category not found "));

        categoryRepository.delete(category);

        return "Delete Category with ID-"+id;
    }


}
