package com.nashtech.rookies.ecommerce.services.prod.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.prods.Category;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CategoryServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryRequestDTO categoryRequestDTO;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setCategoryName("Electronics");
        category.setCategoryDesc("Electronic Items");
        category.setActiveMode(ActiveModeEnum.ACTIVE);
        category.setProducts(Collections.emptySet());

        categoryRequestDTO = new CategoryRequestDTO("Electronics", "Electronic Items", ActiveModeEnum.ACTIVE);
    }

    @Test
    void createCategory() {
        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);

        CategoryResponseDTO response = categoryService.createCategory(categoryRequestDTO);

        assertNotNull(response);
        assertEquals(category.getId(), response.id());
        assertEquals(category.getCategoryName(), response.categoryName());
        verify(categoryRepository).saveAndFlush(any(Category.class));
    }

    @Test
    void getCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<CategoryResponseDTO> categories = categoryService.getCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals(category.getId(), categories.getFirst().id());
        verify(categoryRepository).findAll();
        verify(productRepository).findAll();
    }

    @Test
    void testGetCategoriesById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        List<CategoryResponseDTO> response = categoryService.getCategories(1L);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(category.getId(), response.getFirst().id());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void testGetCategoryById_whenNotFoundId() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.getCategories(1L));
        verify(categoryRepository).findById(1L);
    }

    @Test
    void updateCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);

        CategoryResponseDTO response = categoryService.updateCategory(1L, categoryRequestDTO);

        assertNotNull(response);
        assertEquals(category.getId(), response.id());
        assertEquals(category.getCategoryName(), response.categoryName());
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).saveAndFlush(any(Category.class));
    }

    @AfterEach
    void tearDown() {
    }
}