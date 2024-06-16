package com.nashtech.rookies.ecommerce.controllers.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierGetRequestParamsDTO;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.SupplierService;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/suppliers")
public class SupplierController {
  private final SupplierService supplierService;

  public SupplierController(SupplierService supplierService) {
    this.supplierService = supplierService;
  }

  @PostMapping()
  public ResponseEntity<SupplierResponseDTO> createSupplier(@RequestBody SupplierRequestDTO supplierRequestDTO) {
    return ResponseEntity.ok(supplierService.createSupplier(supplierRequestDTO));
  }

  @GetMapping()
  public ResponseEntity<?> getSupplier(
      @RequestParam(name = "id", required = false) Long id,
      @RequestParam(name = "supplierName", required = false) String supplierName,
      @RequestParam(name = "direction", required = false) Sort.Direction dir,
      @RequestParam(name = "pageNum", required = false) Integer pageNum,
      @RequestParam(name = "pageSize", required = false) Integer pageSize) {
    return supplierService.handleGetSupplier(new SupplierGetRequestParamsDTO(id, supplierName, dir, pageNum, pageSize));
  }

  @PutMapping()
  public ResponseEntity<SupplierResponseDTO> updateSupplier(@RequestParam(name = "id", required = true) Long id,
      @RequestBody SupplierRequestDTO supplierRequestDTO) {
    return ResponseEntity.ok(supplierService.updateSupplier(id, supplierRequestDTO));
  }
}
