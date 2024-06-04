package com.nashtech.rookies.ecommerce.services.prod.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.constants.FeatureModeEnum;
import com.nashtech.rookies.ecommerce.models.prod.Category;
import com.nashtech.rookies.ecommerce.models.prod.Image;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductPaginationDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private Category category;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequestDTO productRequestDTO;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setCategoryName("Electronics");
        category.setCategoryDesc("Electronics Category");
        category.setActiveMode(ActiveModeEnum.ACTIVE);
        category.setProducts(new HashSet<>());

        product = new Product();
        product.setId(1L);
        product.setProductName("Product Name");
        product.setProductDesc("Product Description");
        product.setUnit("Unit");
        product.setPrice(100L);
        product.setQuantity(10L);
        product.setFeatureMode(FeatureModeEnum.FEATURED);
        product.setCategory(category);
        product.setSuppliers(new HashSet<>());
        product.setImages(new HashSet<>());

        productRequestDTO = new ProductRequestDTO("Product Name", "Product Description", "Unit", 100L, 10L,
                FeatureModeEnum.FEATURED, 1L, Set.of());
    }

    @Test
    void testCreateProduct_Success() {
        when(productRepository.existsByProductName(productRequestDTO.productName())).thenReturn(false);
        when(categoryRepository.existsById(productRequestDTO.categoryId())).thenReturn(true);
        when(categoryRepository.findById(productRequestDTO.categoryId())).thenReturn(Optional.of(new Category()));
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);

        ProductResponseDTO responseDTO = productService.createProduct(productRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(product.getId(), responseDTO.id());
        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    void testCreateProduct_Conflict() {
        when(productRepository.existsByProductName(productRequestDTO.productName())).thenReturn(true);
        when(productRepository.findByProductName(productRequestDTO.productName())).thenReturn(product);

        assertThrows(ResourceConflictException.class, () -> productService.createProduct(productRequestDTO));
        verify(productRepository, never()).saveAndFlush(any(Product.class));
    }

    @Test
    void testCreateProduct_CategoryNotFound() {
        when(productRepository.existsByProductName(productRequestDTO.productName())).thenReturn(false);
        when(categoryRepository.existsById(productRequestDTO.categoryId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> productService.createProduct(productRequestDTO));
        verify(productRepository, never()).saveAndFlush(any(Product.class));
    }

    @Test
    void testGetProducts() {
        Page<Product> products = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.findAll(any(Pageable.class))).thenReturn(products);

        ProductPaginationDTO responseDTO = productService.getProducts(Sort.Direction.ASC, 0, 10);

        assertNotNull(responseDTO);
        assertEquals(1, responseDTO.totalElement());
        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }

    // @Test
    // void testGetProductsById_Success() {
    //     when(productRepository.existsById(1L)).thenReturn(true);
    //     when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    //     Page<Product> products = new PageImpl<>(Collections.singletonList(product));
    //     when(productRepository.findAll(any(Pageable.class))).thenReturn(products);

    //     ProductPaginationDTO responseDTO = productService.getProducts(1L);

    //     assertNotNull(responseDTO);
    //     assertEquals(1, responseDTO.totalElement());
    // }

    // @Test
    // void testGetProductsById_NotFound() {
    //     when(productRepository.existsById(1L)).thenReturn(false);

    //     assertThrows(NotFoundException.class, () -> productService.getProducts(1L));
    // }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        Category category = new Category();
        category.setId(1L);

        Set<Supplier> suppliers = new HashSet<>();
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        suppliers.add(supplier);

        ProductRequestDTO updateRequestDTO = new ProductRequestDTO("Updated Product Name", "Updated Product Description",
                "Updated Unit", 200L,
                20L, FeatureModeEnum.FEATURED, 1L, Set.of());

        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.existsById(productRequestDTO.categoryId())).thenReturn(true);
        when(categoryRepository.findById(productRequestDTO.categoryId())).thenReturn(Optional.of(category));
        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);

        // Act
        ProductResponseDTO responseDTO = productService.updateProduct(1L, productRequestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(product.getId(), responseDTO.id());
        assertEquals("Updated Product Name", responseDTO.productName());
        assertEquals("Updated Product Description", responseDTO.productDesc());
        assertEquals("Updated Unit", responseDTO.unit());
        assertEquals(100L, responseDTO.price());
        assertEquals(10L, responseDTO.quantity());
        assertEquals(category.getId(), responseDTO.categoryId());
        assertEquals(suppliers.size(), responseDTO.suppliers().size());
        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> productService.updateProduct(1L, productRequestDTO));
        verify(productRepository, never()).saveAndFlush(any(Product.class));
    }
}
