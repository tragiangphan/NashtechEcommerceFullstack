package com.nashtech.rookies.ecommerce.controllers.prod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.GlobalExceptionHandler;
import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.services.prod.CategoryService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryResponseDTO categoryResponseDTO;
    private CategoryRequestDTO categoryRequestDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).setControllerAdvice(new GlobalExceptionHandler())
        .build();

        categoryService = Mockito.mock(CategoryService.class);
        categoryController = new CategoryController(categoryService);

        categoryRequestDTO = new CategoryRequestDTO("Electronics", "Electronic Items", ActiveModeEnum.ACTIVE);
        categoryResponseDTO = new CategoryResponseDTO(1L, "Electronics", "Electronic Items", ActiveModeEnum.ACTIVE, Set.of());
    }

    @Test
    void testCreateCategory_validInput_returnsCreated() throws Exception {
        // Arrange
        Mockito.when(categoryService.createCategory(categoryRequestDTO)).thenReturn(categoryResponseDTO);

        // Act
        ResponseEntity<CategoryResponseDTO> response = categoryController.createCategory(categoryRequestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryResponseDTO, response.getBody());
        Mockito.verify(categoryService).createCategory(categoryRequestDTO);
    }


    @Test
    void createCategory() throws Exception {
        
    }

    @Test
    void getCategoryMethod() throws Exception {
        
    }

    @Test
    void getCategoryById() throws Exception {
        
    }

    @Test
    void updateCategoryById() throws Exception {
        
    }

    @AfterEach
    void tearDown() {
    }
}
