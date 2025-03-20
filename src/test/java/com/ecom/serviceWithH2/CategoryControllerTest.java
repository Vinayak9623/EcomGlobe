//package com.ecom.serviceWithH2;
//
//import com.ecom.dto.CategoryDto;
//import com.ecom.entity.Category;
//import com.ecom.service.CategoryService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//@SpringBootTest
//@TestPropertySource(locations = "classpath:applicationTest.yml")
//@ExtendWith(MockitoExtension.class)
//public class CategoryControllerTest {
//
//    @InjectMocks
//    CategoryService categoryService;
//
//    @Mock
//    ModelMapper categoryMapper;
//
//
//    @Test
//    void createCat(){
//        Category category=new Category(1l,"Foods");
//        CategoryDto categoryDto=new CategoryDto(1L,"Foods");
//
//        Mockito.when(categoryMapper.map(category,CategoryDto.class)).thenReturn(categoryDto);
//
//        CategoryDto c=categoryService.createCatogry(categoryDto);
//
//
//
//    }
//}
