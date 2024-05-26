package com.nashtech.rookies.ecommerce.services.prod.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.prods.Category;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryRequestDTO categoryRequestDTO;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setCategoryName("Electronics");
        category.setCategoryDesc("Electronics Category");
        category.setActiveMode(ActiveModeEnum.ACTIVE);
        category.setProducts(new HashSet<>());

        categoryRequestDTO = new CategoryRequestDTO("Electronics", "Electronics Category", ActiveModeEnum.ACTIVE);
    }

    @Test
    void testCreateCategory_WhenCategoryDoesNotExist_ShouldCreateNewCategory() {
        when(categoryRepository.existsByCategoryName(categoryRequestDTO.categoryName())).thenReturn(false);
        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);

        CategoryResponseDTO responseDTO = categoryService.createCategory(categoryRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(category.getId(), responseDTO.id());
        verify(categoryRepository, times(1)).saveAndFlush(any(Category.class));
    }

    @Test
    void testCreateCategory_WhenCategoryExists_ShouldThrowResourceConflictException() {
        when(categoryRepository.existsByCategoryName(categoryRequestDTO.categoryName())).thenReturn(true);
        when(categoryRepository.findByCategoryName(categoryRequestDTO.categoryName())).thenReturn(category);

        assertThrows(ResourceConflictException.class, () -> categoryService.createCategory(categoryRequestDTO));
        verify(categoryRepository, never()).saveAndFlush(any(Category.class));
    }

    @Test
    void testGetCategories_ShouldReturnAllCategories() {
        List<Category> categories = Collections.singletonList(category);
        List<Product> products = Collections.emptyList();
        when(categoryRepository.findAll()).thenReturn(categories);
        when(productRepository.findAll()).thenReturn(products);

        List<CategoryResponseDTO> responseDTOs = categoryService.getCategories();

        assertNotNull(responseDTOs);
        assertEquals(1, responseDTOs.size());
        assertEquals(category.getId(), responseDTOs.getFirst().id());
    }

    @Test
    void testGetCategoryById_WhenCategoryExists_ShouldReturnCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        List<CategoryResponseDTO> responseDTOs = categoryService.getCategories(1L);

        assertNotNull(responseDTOs);
        assertEquals(1, responseDTOs.size());
        assertEquals(category.getId(), responseDTOs.getFirst().id());
    }

    @Test
    void testGetCategoryById_WhenCategoryDoesNotExist_ShouldThrowNotFoundException() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> categoryService.getCategories(1L));
    }

    @Test
    void testUpdateCategory_WhenCategoryExists_ShouldUpdateCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);

        CategoryResponseDTO responseDTO = categoryService.updateCategory(1L, categoryRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(category.getId(), responseDTO.id());
        verify(categoryRepository, times(1)).saveAndFlush(any(Category.class));
    }

    @Test
    void testUpdateCategory_WhenCategoryDoesNotExist_ShouldThrowNotFoundException() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> categoryService.updateCategory(1L, categoryRequestDTO));
        verify(categoryRepository, never()).saveAndFlush(any(Category.class));
    }
}
