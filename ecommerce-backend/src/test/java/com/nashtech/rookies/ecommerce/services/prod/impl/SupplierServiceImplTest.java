package com.nashtech.rookies.ecommerce.services.prod.impl;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierPaginationDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private SupplierRepository supplierRepository;

  @InjectMocks
  private SupplierServiceImpl supplierServiceImpl;

  private SupplierGetRequestParamsDTO requestParamsDTO;
  private Sort.Direction dir = Sort.Direction.ASC;
  private int pageNum = 1;
  private int pageSize = 10;
  private Set<Product> products;
  private Supplier supplier;
  private Product product1;
  private Product product2;
  private SupplierRequestDTO supplierRequestDTO;

  @BeforeEach
  void setUp() {
    product1 = new Product();
    product1.setId(1L);
    product1.setProductName("Product1");

    product2 = new Product();
    product2.setId(2L);
    product2.setProductName("Product2");

    supplier = new Supplier();
    supplier.setId(1L);
    supplier.setSupplierName("Updated Supplier");
    supplier.setPhoneNo("9876543210");
    supplier.setEmail("updated@supplier.com");
    supplier.setAddress("456 Updated Address");
    supplier.setStreet("Updated Street");
    supplier.setWard("Updated Ward");
    supplier.setCity("Updated City");
    supplier.setCountry("Updated Country");
    supplier.setPostalCode("67890");
    supplier.setActiveMode(ActiveModeEnum.INACTIVE);
    supplier.setProducts(Set.of(product1, product2));

    supplierRequestDTO = new SupplierRequestDTO("Updated Supplier", "9876543210", "updated@supplier.com",
        "456 Updated Address", "Updated Street", "Updated Ward", "Updated City", "Updated Country", "67890",ActiveModeEnum.INACTIVE, Set.of(1L, 2L));
  }

  @Test
  void testCreateSupplier_WhenSupplierExists_ThenThrowResourceConflictException() {
    // Given
    Supplier existingSupplier = new Supplier();
    existingSupplier.setId(1L);
    existingSupplier.setSupplierName("Test Supplier");

    // When
    when(supplierRepository.existsBySupplierName(supplierRequestDTO.supplierName())).thenReturn(true);
    when(supplierRepository.findBySupplierName(supplierRequestDTO.supplierName())).thenReturn(existingSupplier);

    // Then
    assertThatThrownBy(() -> supplierServiceImpl.createSupplier(supplierRequestDTO))
        .isInstanceOf(ResourceConflictException.class)
        .hasMessageContaining("Existed Supplier Name with an id: 1");

    verify(supplierRepository, times(1)).existsBySupplierName(supplierRequestDTO.supplierName());
    verify(supplierRepository, times(1)).findBySupplierName(supplierRequestDTO.supplierName());
    verify(supplierRepository, never()).saveAndFlush(any(Supplier.class));
  }

  @Test
  void testCreateSupplier_WhenSupplierDoesNotExist_ThenReturnNewSupplier() {
    // When
    when(supplierRepository.existsBySupplierName(supplierRequestDTO.supplierName())).thenReturn(false);
    Supplier savedSupplier = getSupplier(supplierRequestDTO);
    when(supplierRepository.saveAndFlush(any(Supplier.class))).thenReturn(savedSupplier);
    SupplierResponseDTO response = supplierServiceImpl.createSupplier(supplierRequestDTO);

    // Then
    assertThat(response).isNotNull()
        .hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("supplierName", "Updated Supplier")
        .hasFieldOrPropertyWithValue("phoneNo", "9876543210")
        .hasFieldOrPropertyWithValue("email", "updated@supplier.com")
        .hasFieldOrPropertyWithValue("address", "456 Updated Address")
        .hasFieldOrPropertyWithValue("street", "Updated Street")
        .hasFieldOrPropertyWithValue("ward", "Updated Ward")
        .hasFieldOrPropertyWithValue("city", "Updated City")
        .hasFieldOrPropertyWithValue("country", "Updated Country")
        .hasFieldOrPropertyWithValue("postalCode", "67890")
        .hasFieldOrPropertyWithValue("activeMode", ActiveModeEnum.INACTIVE)
        .hasFieldOrPropertyWithValue("products", new HashSet<>());

    verify(supplierRepository, times(1)).existsBySupplierName(supplierRequestDTO.supplierName());
    verify(supplierRepository, never()).findBySupplierName(supplierRequestDTO.supplierName());
    verify(supplierRepository, times(1)).saveAndFlush(any(Supplier.class));
  }

  @Test
  void testCreateSupplier_WithNullOptionalFields() {
    // Given
    SupplierRequestDTO supplierRequestDTOWithNulls = new SupplierRequestDTO(
        "Test Supplier", "1234567890", "test@supplier.com",
        null, null, null,
        null, null, null, ActiveModeEnum.ACTIVE, new HashSet<>());

    // when
    when(supplierRepository.existsBySupplierName(supplierRequestDTOWithNulls.supplierName())).thenReturn(false);
    Supplier savedSupplier = getSupplier(supplierRequestDTOWithNulls);
    when(supplierRepository.saveAndFlush(any(Supplier.class))).thenReturn(savedSupplier);
    SupplierResponseDTO response = supplierServiceImpl.createSupplier(supplierRequestDTOWithNulls);

    // Then
    assertThat(response).isNotNull()
        .hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("supplierName", "Test Supplier")
        .hasFieldOrPropertyWithValue("phoneNo", "1234567890")
        .hasFieldOrPropertyWithValue("email", "test@supplier.com")
        .hasFieldOrPropertyWithValue("address", null)
        .hasFieldOrPropertyWithValue("street", null)
        .hasFieldOrPropertyWithValue("ward", null)
        .hasFieldOrPropertyWithValue("city", null)
        .hasFieldOrPropertyWithValue("country", null)
        .hasFieldOrPropertyWithValue("postalCode", null)
        .hasFieldOrPropertyWithValue("activeMode", ActiveModeEnum.ACTIVE)
        .hasFieldOrPropertyWithValue("products", new HashSet<>());

    verify(supplierRepository, times(1)).existsBySupplierName(supplierRequestDTOWithNulls.supplierName());
    verify(supplierRepository, never()).findBySupplierName(supplierRequestDTOWithNulls.supplierName());
    verify(supplierRepository, times(1)).saveAndFlush(any(Supplier.class));
  }

  private static Supplier getSupplier(SupplierRequestDTO supplierRequestDTOWithNulls) {
    Supplier savedSupplier = new Supplier(
        supplierRequestDTOWithNulls.supplierName(), supplierRequestDTOWithNulls.phoneNo(),
        supplierRequestDTOWithNulls.email(), supplierRequestDTOWithNulls.address(),
        supplierRequestDTOWithNulls.street(), supplierRequestDTOWithNulls.ward(),
        supplierRequestDTOWithNulls.city(), supplierRequestDTOWithNulls.country(),
        supplierRequestDTOWithNulls.postalCode(), supplierRequestDTOWithNulls.activeMode(),
        new HashSet<>());
    savedSupplier.setId(1L);
    return savedSupplier;
  }

  @Test
  void testHandleGetSupplier_WhenIdProvided_ShouldReturnSupplierResponseDTO() {
    // Given
    Long id = 1L;
    requestParamsDTO = new SupplierGetRequestParamsDTO(id, null, null, null, null, null);
    supplier.setId(id);

    // When
    when(supplierRepository.existsById(id)).thenReturn(true);
    when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));
    ResponseEntity<?> response = supplierServiceImpl.handleGetSupplier(requestParamsDTO);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isInstanceOf(SupplierResponseDTO.class);
    SupplierResponseDTO responseDTO = (SupplierResponseDTO) response.getBody();
    assertThat(responseDTO).isNotNull()
        .hasFieldOrPropertyWithValue("id", id)
        .hasFieldOrPropertyWithValue("supplierName", "Updated Supplier")
        .hasFieldOrPropertyWithValue("phoneNo", "9876543210")
        .hasFieldOrPropertyWithValue("email", "updated@supplier.com")
        .hasFieldOrPropertyWithValue("address", "456 Updated Address")
        .hasFieldOrPropertyWithValue("street", "Updated Street")
        .hasFieldOrPropertyWithValue("ward", "Updated Ward")
        .hasFieldOrPropertyWithValue("city", "Updated City")
        .hasFieldOrPropertyWithValue("country", "Updated Country")
        .hasFieldOrPropertyWithValue("postalCode", "67890")
        .hasFieldOrPropertyWithValue("activeMode", ActiveModeEnum.INACTIVE)
        .hasFieldOrPropertyWithValue("products", Set.of(1L, 2L));

    verify(supplierRepository, times(1)).existsById(id);
    verify(supplierRepository, times(1)).findById(id);
  }

  @Test
  void testHandleGetSupplier_WhenSupplierNameProvided_ShouldReturnSupplierResponseDTO() {
    // Given
    String supplierName = "Test Supplier";
    requestParamsDTO = new SupplierGetRequestParamsDTO(null, supplierName, null, null, null, null);

    // When
    when(supplierRepository.findBySupplierName(supplierName)).thenReturn(supplier);
    ResponseEntity<?> response = supplierServiceImpl.handleGetSupplier(requestParamsDTO);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isInstanceOf(SupplierResponseDTO.class);
    SupplierResponseDTO responseDTO = (SupplierResponseDTO) response.getBody();
    assertThat(responseDTO).isNotNull()
        .hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("supplierName", "Updated Supplier")
        .hasFieldOrPropertyWithValue("phoneNo", "9876543210")
        .hasFieldOrPropertyWithValue("email", "updated@supplier.com")
        .hasFieldOrPropertyWithValue("address", "456 Updated Address")
        .hasFieldOrPropertyWithValue("street", "Updated Street")
        .hasFieldOrPropertyWithValue("ward", "Updated Ward")
        .hasFieldOrPropertyWithValue("city", "Updated City")
        .hasFieldOrPropertyWithValue("country", "Updated Country")
        .hasFieldOrPropertyWithValue("postalCode", "67890")
        .hasFieldOrPropertyWithValue("activeMode", ActiveModeEnum.INACTIVE)
        .hasFieldOrPropertyWithValue("products", Set.of(1L, 2L));

    verify(supplierRepository, times(1)).findBySupplierName(supplierName);
  }

  @Test
  void testHandleGetSupplier_WhenNoParamsProvided_ShouldReturnSupplierPaginationDTO() {
    // Given
    requestParamsDTO = new SupplierGetRequestParamsDTO(null, null, null, dir, pageNum,
        pageSize);
    Page<Supplier> suppliersPage = new PageImpl<>(List.of(supplier),
        PageRequest.of(pageNum - 1, pageSize, Sort.by(dir, "id")), pageNum - 1);

    // When
    when(supplierRepository.findAll(any(PageRequest.class))).thenReturn(suppliersPage);
    ResponseEntity<?> response = supplierServiceImpl.handleGetSupplier(requestParamsDTO);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isInstanceOf(SupplierPaginationDTO.class);
    SupplierPaginationDTO responseDTO = (SupplierPaginationDTO) response.getBody();
    assertThat(responseDTO).isNotNull();
    assertThat(responseDTO.totalPage()).isEqualTo(1);
    assertThat(responseDTO.totalElement()).isEqualTo(1);
    assertThat(responseDTO.pageSize()).isEqualTo(10);
    assertThat(responseDTO.pageNum()).isZero();
    assertThat(responseDTO.supplierResponseDTOs()).hasSize(1);
    assertThat(responseDTO.supplierResponseDTOs().get(0))
        .hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("supplierName", "Updated Supplier")
        .hasFieldOrPropertyWithValue("phoneNo", "9876543210")
        .hasFieldOrPropertyWithValue("email", "updated@supplier.com")
        .hasFieldOrPropertyWithValue("address", "456 Updated Address")
        .hasFieldOrPropertyWithValue("street", "Updated Street")
        .hasFieldOrPropertyWithValue("ward", "Updated Ward")
        .hasFieldOrPropertyWithValue("city", "Updated City")
        .hasFieldOrPropertyWithValue("country", "Updated Country")
        .hasFieldOrPropertyWithValue("postalCode", "67890")
        .hasFieldOrPropertyWithValue("activeMode", ActiveModeEnum.INACTIVE)
        .hasFieldOrPropertyWithValue("products", Set.of(1L, 2L));

    verify(supplierRepository, times(1)).findAll(any(PageRequest.class));
  }

  @Test
  void testGetSupplierById_WhenSupplierNotFound_ShouldThrowNotFoundException() {
    // Given
    Long id = 1L;

    // When
    when(supplierRepository.existsById(id)).thenReturn(false);

    // Then
    assertThatThrownBy(() -> supplierServiceImpl.getSupplierById(id))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Not found Supplier with an id: " + id);

    verify(supplierRepository, times(1)).existsById(id);
    verify(supplierRepository, never()).findById(id);
  }

  @Test
  void testGetSupplierBySupplierName_WhenSupplierNotFound_ShouldThrowNotFoundException() {
    // Given
    String supplierName = "Nonexistent Supplier";

    // When
    when(supplierRepository.findBySupplierName(supplierName)).thenReturn(null);

    // Then
    assertThatThrownBy(() -> supplierServiceImpl.getSupplierBySupplierName(supplierName))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Not found Supplier with a name: " + supplierName);

    verify(supplierRepository, times(1)).findBySupplierName(supplierName);
  }

  @Test
  void testGetSupplierById_WithProducts_ShouldReturnSupplierResponseDTO() {
    // Given
    Long id = 1L;

    // When
    when(supplierRepository.existsById(id)).thenReturn(true);
    when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));
    SupplierResponseDTO responseDTO = supplierServiceImpl.getSupplierById(id);

    // Then
    assertThat(responseDTO).isNotNull()
        .hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("supplierName", "Updated Supplier")
        .hasFieldOrPropertyWithValue("phoneNo", "9876543210")
        .hasFieldOrPropertyWithValue("email", "updated@supplier.com")
        .hasFieldOrPropertyWithValue("address", "456 Updated Address")
        .hasFieldOrPropertyWithValue("street", "Updated Street")
        .hasFieldOrPropertyWithValue("ward", "Updated Ward")
        .hasFieldOrPropertyWithValue("city", "Updated City")
        .hasFieldOrPropertyWithValue("country", "Updated Country")
        .hasFieldOrPropertyWithValue("postalCode", "67890")
        .hasFieldOrPropertyWithValue("activeMode", ActiveModeEnum.INACTIVE)
        .hasFieldOrPropertyWithValue("products", Set.of(1L, 2L));

    assertThat(responseDTO.products()).containsExactlyInAnyOrder(1L, 2L);

    verify(supplierRepository, times(1)).existsById(id);
    verify(supplierRepository, times(1)).findById(id);
  }

  @Test
  void testUpdateSupplier_WhenSupplierExistsAndProductsExist_ReturnUpdatedSupplierResponseDTO() {
    // Given
    when(supplierRepository.existsById(1L)).thenReturn(true);
    when(productRepository.existsById(1L)).thenReturn(true);
    when(productRepository.existsById(2L)).thenReturn(true);
    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
    when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
    when(productRepository.findById(product2.getId())).thenReturn(Optional.of(product2));
    when(supplierRepository.saveAndFlush(any(Supplier.class))).thenReturn(supplier);

    // When
    SupplierResponseDTO responseDTO = supplierServiceImpl.updateSupplier(1L, supplierRequestDTO);

    // Then
    assertThat(responseDTO).isNotNull()
        .hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("supplierName", "Updated Supplier")
        .hasFieldOrPropertyWithValue("phoneNo", "9876543210")
        .hasFieldOrPropertyWithValue("email", "updated@supplier.com")
        .hasFieldOrPropertyWithValue("address", "456 Updated Address")
        .hasFieldOrPropertyWithValue("street", "Updated Street")
        .hasFieldOrPropertyWithValue("ward", "Updated Ward")
        .hasFieldOrPropertyWithValue("city", "Updated City")
        .hasFieldOrPropertyWithValue("country", "Updated Country")
        .hasFieldOrPropertyWithValue("postalCode", "67890")
        .hasFieldOrPropertyWithValue("activeMode", ActiveModeEnum.INACTIVE);

    // assertThat(supplierRequestDTO.products()).isNull();

    assertThat(responseDTO.products()).containsExactlyInAnyOrder(1L, 2L);

    verify(supplierRepository, times(1)).existsById(1L);
    verify(supplierRepository, times(1)).findById(1L);
    verify(productRepository, times(1)).findById(product1.getId());
    verify(productRepository, times(1)).findById(product2.getId());
    verify(supplierRepository, times(1)).saveAndFlush(supplier);
  }

  @Test
  void testUpdateSupplier_WhenSupplierDoesNotExist_ReturnNotFoundException() {
    // Given
    when(supplierRepository.existsById(1L)).thenReturn(false);

    // When / Then
    assertThrows(NotFoundException.class, () -> {
      supplierServiceImpl.updateSupplier(1L, supplierRequestDTO);
    });

    verify(supplierRepository, times(1)).existsById(1L);
    verify(supplierRepository, times(0)).findById(1L);
    verify(productRepository, times(0)).findById(anyLong());
    verify(supplierRepository, times(0)).saveAndFlush(any(Supplier.class));
  }

  @Test
  void testUpdateSupplier_WhenProductDoesNotExist_ReturnNotFoundException() {
    // Given
    when(supplierRepository.existsById(1L)).thenReturn(true);
    lenient().when(productRepository.existsById(1L)).thenReturn(false);
    lenient().when(productRepository.existsById(2L)).thenReturn(false);

    // When / Then
    assertThrows(NotFoundException.class, () -> {
      supplierServiceImpl.updateSupplier(1L, supplierRequestDTO);
    });

    verify(supplierRepository, times(1)).existsById(1L);
    verify(supplierRepository, times(0)).findById(1L);
    verify(productRepository, times(0)).findById(product1.getId());
    verify(productRepository, times(0)).findById(product2.getId());
    verify(supplierRepository, times(0)).saveAndFlush(any(Supplier.class));
  }

}
