package com.nashtech.rookies.ecommerce.services.prod.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;
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
    supplier.setSupplierName("Test Supplier");
    supplier.setPhoneNo("1234567890");
    supplier.setEmail("test@supplier.com");
    supplier.setAddress("123 Test St.");
    supplier.setStreet("Test St.");
    supplier.setWard("Test Ward");
    supplier.setCity("Test City");
    supplier.setCountry("Test Country");
    supplier.setPostalCode("12345");
    supplier.setActiveMode(ActiveModeEnum.ACTIVE);
    supplier.setProducts(new HashSet<>());

    supplierRequestDTO = new SupplierRequestDTO(
        "Test Supplier",
        "1234567890",
        "test@supplier.com",
        "123 Test St.",
        "Test St.",
        "Test Ward",
        "Test City",
        "Test Country",
        "12345",
        ActiveModeEnum.ACTIVE,
        new HashSet<>());
  }

  @Test
  void createSupplier_WhenDoesNotExistSupplierName_CreateNewSupplier() {
    when(supplierRepository.existsBySupplierName(supplierRequestDTO.supplierName())).thenReturn(false);
    when(supplierRepository.saveAndFlush(any(Supplier.class))).thenReturn(supplier);

    SupplierResponseDTO result = supplierService.createSupplier(supplierRequestDTO);

    assertThat(result).isNotNull()
        .hasFieldOrPropertyWithValue("id", 1L) 
        .hasFieldOrPropertyWithValue("supplierName", supplier.getSupplierName())
        .hasFieldOrPropertyWithValue("phoneNo", supplier.getPhoneNo())
        .hasFieldOrPropertyWithValue("email", supplier.getEmail())
        .hasFieldOrPropertyWithValue("address", supplier.getAddress())
        .hasFieldOrPropertyWithValue("street", supplier.getStreet())
        .hasFieldOrPropertyWithValue("ward", supplier.getWard())
        .hasFieldOrPropertyWithValue("city", supplier.getCity())
        .hasFieldOrPropertyWithValue("country", supplier.getCountry())
        .hasFieldOrPropertyWithValue("postalCode", supplier.getPostalCode())
        .hasFieldOrPropertyWithValue("activeMode", supplier.getActiveMode())
        .hasFieldOrPropertyWithValue("products", new HashSet<>());
    verify(supplierRepository, times(1)).saveAndFlush(any(Supplier.class));
  }

  @Test
  void createSupplier_WhenExistedSupplierName_ThrowResourceConflictException() {
    when(supplierRepository.existsBySupplierName(supplierRequestDTO.supplierName())).thenReturn(true);
    when(supplierRepository.findBySupplierName(supplierRequestDTO.supplierName())).thenReturn(supplier);

    Throwable thrown = catchThrowable(() -> supplierService.createSupplier(supplierRequestDTO));

    assertThat(thrown).isInstanceOf(ResourceConflictException.class)
        .hasMessageContaining("Existed Supplier Name with an id: " + supplier.getId());
  }

  @Test
  void getSupplierById_WhenExistedSupplier_ReturnSupplier() {
    when(supplierRepository.existsById(supplier.getId())).thenReturn(true);
    when(supplierRepository.findById(supplier.getId())).thenReturn(Optional.of(supplier));

    SupplierResponseDTO result = supplierService.getSupplierById(supplier.getId());

    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(supplier.getId());
    verify(supplierRepository, times(1)).findById(supplier.getId());
  }

  @Test
  void getSupplierById_WhenDoesNotExistSupplier_ThrowNotFoundException() {
    when(supplierRepository.existsById(supplier.getId())).thenReturn(false);

    Throwable thrown = catchThrowable(() -> supplierService.getSupplierById(supplier.getId()));

    assertThat(thrown).isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Not found Supplier with an id: " + supplier.getId());
  }

  @Test
  void updateSupplier_WhenDoesNotExistSupplierName_ThrowNotFoundException() {
    lenient().when(supplierRepository.existsById(supplier.getId())).thenReturn(true);
    lenient().when(supplierRepository.findById(supplier.getId())).thenReturn(Optional.of(supplier));
    lenient().when(productRepository.existsById(anyLong())).thenReturn(true);
    lenient().when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));
    lenient().when(supplierRepository.saveAndFlush(any(Supplier.class))).thenReturn(supplier);

    SupplierResponseDTO result = supplierService.updateSupplier(supplier.getId(), supplierRequestDTO);

    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(supplier.getId());
    verify(supplierRepository, times(1)).saveAndFlush(any(Supplier.class));
  }

  @Test
  void updateSupplier_WhenExistedSupplierName_ReturnSupplierUpdated() {
    when(supplierRepository.existsById(supplier.getId())).thenReturn(false);

    Throwable thrown = catchThrowable(() -> supplierService.updateSupplier(supplier.getId(), supplierRequestDTO));

    assertThat(thrown).isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Not found Supplier with an id: " + supplier.getId());
  }
}
