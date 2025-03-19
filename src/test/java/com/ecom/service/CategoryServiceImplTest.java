package com.ecom.service;

import com.ecom.Repository.CategoryRepository;
import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.customException.CategoryNotFoundException;
import com.ecom.serviceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category(1L, "Electronics");
        categoryDto = new CategoryDto(1L, "Electronics");
    }

    @Test
    void testCreateCategory() {

        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDto createdCategory = categoryService.createCatogry(categoryDto);

        assertNotNull(createdCategory);
        assertEquals("Electronics", createdCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testGetCategories() {
        Category category2 = new Category(2L, "Clothing");
        CategoryDto categoryDto2 = new CategoryDto(2L, "Clothing");
        when(categoryRepository.findAll()).thenReturn(List.of(category, category2));
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);
        when(modelMapper.map(category2, CategoryDto.class)).thenReturn(categoryDto2);

        List<CategoryDto> categoryDtos = categoryService.getCategories();


        assertEquals(2, categoryDtos.size());
        assertEquals("Electronics", categoryDtos.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCategory() {
        Category updatedCategory = new Category(1L, "Updated Electronics");
        CategoryDto updatedCategoryDto = new CategoryDto(1L, "Updated Electronics");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        when(modelMapper.map(updatedCategory, CategoryDto.class)).thenReturn(updatedCategoryDto);


        CategoryDto result = categoryService.updateCategory(updatedCategoryDto, 1L);


        assertNotNull(result);
        assertEquals("Updated Electronics", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteCategory() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        String response = categoryService.deleteCategory(1L);

        assertEquals("Delete Category with ID-1", response);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testCategoryNotFoundException() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(categoryDto, 99L));
    }
}
