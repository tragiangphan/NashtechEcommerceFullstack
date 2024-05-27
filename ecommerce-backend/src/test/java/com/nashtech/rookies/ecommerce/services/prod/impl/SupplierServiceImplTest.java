package com.nashtech.rookies.ecommerce.services.prod.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.mappers.prod.SupplierMapper;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;
    private SupplierRequestDTO supplierRequestDTO;

    @BeforeEach
    void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setSupplierName("Supplier Name");
        supplier.setPhoneNo("1234567890");
        supplier.setEmail("supplier@example.com");
        supplier.setAddress("123/45");
        supplier.setStreet("Main St");
        supplier.setWard("Ward 1");
        supplier.setCity("City");
        supplier.setCountry("Country");
        supplier.setPostalCode("12345");
        supplier.setActiveMode(ActiveModeEnum.ACTIVE);
        supplier.setProducts(new HashSet<>());

        supplierRequestDTO = new SupplierRequestDTO("Supplier Name", "1234567890", "supplier@example.com", "123/45", "Main St", "Ward 1", "City", "Country", "12345", ActiveModeEnum.ACTIVE, new HashSet<>());
    }

    @Test
    void testCreateSupplier_WhenSupplierDoesNotExist_ShouldCreateNewSupplier() {
        when(supplierRepository.existsBySupplierName(supplierRequestDTO.supplierName())).thenReturn(false);
        when(supplierRepository.saveAndFlush(any(Supplier.class))).thenReturn(supplier);

        SupplierResponseDTO responseDTO = supplierService.createSupplier(supplierRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(supplier.getId(), responseDTO.id());
        verify(supplierRepository, times(1)).saveAndFlush(any(Supplier.class));
    }

    @Test
    void testCreateSupplier_WhenSupplierExists_ShouldThrowResourceConflictException() {
        when(supplierRepository.existsBySupplierName(supplierRequestDTO.supplierName())).thenReturn(true);
        when(supplierRepository.findBySupplierName(supplierRequestDTO.supplierName())).thenReturn(supplier);

        assertThrows(ResourceConflictException.class, () -> supplierService.createSupplier(supplierRequestDTO));
        verify(supplierRepository, never()).saveAndFlush(any(Supplier.class));
    }

    @Test
    void testGetSuppliers_ShouldReturnAllSuppliers() {
        List<Supplier> suppliers = Collections.singletonList(supplier);
        List<Product> products = Collections.emptyList();
        when(supplierRepository.findAll()).thenReturn(suppliers);
        when(productRepository.findAll()).thenReturn(products);

        List<SupplierResponseDTO> responseDTOs = supplierService.getSuppliers();

        assertNotNull(responseDTOs);
        assertEquals(1, responseDTOs.size());
        assertEquals(supplier.getId(), responseDTOs.getFirst().id());
    }

    @Test
    void testGetSupplierById_WhenSupplierExists_ShouldReturnSupplier() {
        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        List<SupplierResponseDTO> responseDTOs = supplierService.getSuppliers(1L);

        assertNotNull(responseDTOs);
        assertEquals(1, responseDTOs.size());
        assertEquals(supplier.getId(), responseDTOs.getFirst().id());
    }

    @Test
    void testGetSupplierById_WhenSupplierDoesNotExists_ShouldThrowNotFoundException() {
        when(supplierRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> supplierService.getSuppliers(1L));
    }

    @Test
    void testUpdateSupplier_WhenSupplierExists_ShouldUpdateSupplier() {
        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(supplierRepository.saveAndFlush(any(Supplier.class))).thenReturn(supplier);

        SupplierResponseDTO responseDTO = supplierService.updateSupplier(1L, supplierRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(supplier.getId(), responseDTO.id());
        verify(supplierRepository, times(1)).saveAndFlush(any(Supplier.class));
    }

    @Test
    void testUpdateSupplier__WhenSupplierExists_ShouldThrowNotFoundException() {
        when(supplierRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> supplierService.updateSupplier(1L, supplierRequestDTO));
        verify(supplierRepository, never()).saveAndFlush(any(Supplier.class));
    }
}
