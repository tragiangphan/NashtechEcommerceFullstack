package com.nashtech.rookies.ecommerce.controllers.prod;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.SupplierService;

import jakarta.validation.Valid;

@RestController
public class SupplierController extends RestVersion {
  private final SupplierService supplierService;

  public SupplierController(SupplierService supplierService) {
    this.supplierService = supplierService;
  }

  @PostMapping("/suppliers")
  public ResponseEntity<SupplierResponseDTO> createSupplier(@RequestBody @Valid SupplierRequestDTO supplierRequestDTO) {
    return ResponseEntity.ok(supplierService.createSupplier(supplierRequestDTO));
  }

  @GetMapping("/suppliers")
  public ResponseEntity<List<SupplierResponseDTO>> getSupplier(@RequestParam(name = "id", required = false) Long id) {
    List<SupplierResponseDTO> supplierResponseDTO;
    
    if (id != null) {
      supplierResponseDTO = supplierService.getSuppliers(id);
    } else {
      supplierResponseDTO = supplierService.getSuppliers();
    }
    return ResponseEntity.ok(supplierResponseDTO);
  }

  @PutMapping("/suppliers")
  public ResponseEntity<SupplierResponseDTO> updateSupplier(@RequestParam(name = "id", required = true) Long id,
      @RequestBody SupplierRequestDTO supplierRequestDTO) {
    return ResponseEntity.ok(supplierService.updateSupplier(id, supplierRequestDTO));
  }
}
