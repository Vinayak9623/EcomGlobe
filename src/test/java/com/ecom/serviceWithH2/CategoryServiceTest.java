package com.ecom.serviceWithH2;

import com.ecom.Repository.CategoryRepository;
import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.serviceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:applicationTest.yml")
public class CategoryServiceTest {


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

        Category category1 = new Category(2l, "Fruits");
        CategoryDto categoryDto1 = new CategoryDto(2l, "Fruits");

        when(categoryRepository.findAll()).thenReturn(List.of(category,category1));
        when(modelMapper.map(category,CategoryDto.class)).thenReturn(categoryDto);
        when(modelMapper.map(category1,CategoryDto.class)).thenReturn(categoryDto1);


        List<CategoryDto> categoryDtos=categoryService.getCategories();

        assertNotNull(categoryDtos);
        assertEquals(2,categoryDtos.size());
        verify(categoryRepository,times(1)).findAll();


    }

}
