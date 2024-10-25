package com.swp391.koibe.services.category;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.swp391.koibe.dtos.CategoryDTO;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CategoryServiceTest {

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        reset(categoryService);
    }

    @Test
    @Order(1)
    void createCategory() throws Exception {
        CategoryDTO newCategory = CategoryDTO.builder()
            .name("Test Category")
            .build();

        Category createdCategory = new Category();
        createdCategory.setName("Test Category");

        when(categoryService.createCategory(newCategory)).thenReturn(createdCategory);

        Category result = categoryService.createCategory(newCategory);
        assertNotNull(result);
        assertEquals("Test Category", result.getName());
        verify(categoryService, times(1)).createCategory(newCategory);
    }

    @Test
    @Order(3)
    void getById() throws DataNotFoundException {
        when(categoryService.getById(2)).thenThrow(new DataNotFoundException("Category not found"));

        assertThrows(DataNotFoundException.class, () -> {
            categoryService.getById(2);
        });
    }

    @Test
    @Order(2)
    void getAllCategories() {
        Category category = new Category();
        category.setName("Test Category");

        when(categoryService.getAllCategories()).thenReturn(Collections.singletonList(category));

        assertEquals(1, categoryService.getAllCategories().size());
    }

    @Test
    @Order(4)
    void update() throws DataNotFoundException {
        categoryService.update(1, CategoryDTO.builder().name("Updated Test").build());
        verify(categoryService, times(1)).update(1, CategoryDTO.builder().name("Updated Test").build());
    }

    @Test
    @Order(5)
    void delete() {
        categoryService.delete(1);
        verify(categoryService, times(1)).delete(1);
    }
}
