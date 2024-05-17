package com.nashtech.rookies.ecommerce.controllers.prod;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.mappers.prod.SupplierMapper;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;
import com.nashtech.rookies.ecommerce.services.prod.SupplierService;

import jakarta.validation.Valid;

@RestController
public class SupplierController extends RestVersion {
  private final SupplierService supplierService;
  private final SupplierMapper supplierMapper;

  public SupplierController(SupplierService supplierService, SupplierMapper supplierMapper) {
    this.supplierService = supplierService;
    this.supplierMapper = supplierMapper;
  }

  @PostMapping("/suppliers")
  public ResponseEntity<Void> createSupplier(@RequestBody @Valid SupplierRequestDTO supplierRequestDTO) {
    Supplier supplier = supplierService.save(supplierMapper.toRequestEntity(supplierRequestDTO));
    return ResponseEntity.created(URI.create("/api/v1/suppliers/" + supplier.getId())).build();
  }

  @GetMapping("/suppliers")
  public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers() {
    var suppliers = supplierService.findAll();
    var supplierResponseDTO = new LinkedList<SupplierResponseDTO>();
    for (var supplier : suppliers) {
      SupplierResponseDTO supplierDTO = supplierMapper.toResponseDTO(supplier);
      supplierResponseDTO.add(supplierDTO);
    }
    return ResponseEntity.ok(supplierResponseDTO);
  }

  @GetMapping("/suppliers/{id}")
  public ResponseEntity<SupplierResponseDTO> getSupplierById(@RequestParam("id") @PathVariable("id") Long id) {
    Supplier supplier = supplierService.findOne(id).orElseThrow(IllegalArgumentException::new);
    SupplierResponseDTO supplierResponseDTO = supplierMapper.toResponseDTO(supplier);
    return ResponseEntity.ok(supplierResponseDTO);
  }

  @PutMapping("/suppliers/{id}")
  public ResponseEntity<SupplierResponseDTO> updateSupplierById(@RequestParam("id") @PathVariable Long id,
      @RequestBody SupplierRequestDTO supplierRequestDTO) {
    Supplier supplier = supplierService.findOne(id).orElseThrow(IllegalArgumentException::new);
    supplier.setSupplierName(supplierRequestDTO.supplierName());
    supplier.setPhoneNo(supplierRequestDTO.phoneNo());
    supplier.setEmail(supplierRequestDTO.email());
    supplier.setAddress(supplierRequestDTO.address());
    supplier.setStreet(supplierRequestDTO.street());
    supplier.setWard(supplierRequestDTO.ward());
    supplier.setCity(supplierRequestDTO.city());
    supplier.setCountry(supplierRequestDTO.country());
    supplier.setPostalCode(supplierRequestDTO.postalCode());
    supplier.setActive(supplierRequestDTO.isActive());
    supplier = supplierService.save(supplier);
    return ResponseEntity.ok(supplierMapper.toResponseDTO(supplier));
  }
}
