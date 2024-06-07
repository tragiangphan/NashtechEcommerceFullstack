package com.nashtech.rookies.ecommerce.services.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierPaginationDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;
import com.nashtech.rookies.ecommerce.services.CommonService;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

public interface SupplierService extends CommonService<Supplier, Long> {
  SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO);

  ResponseEntity<?> handleGetSupplier(SupplierGetRequestParamsDTO supplierGetRequestParamsDTO);

  SupplierPaginationDTO getSuppliers(Sort.Direction dir, int pageNum, int pageSize);

  SupplierResponseDTO getSupplierById(Long id);

  SupplierResponseDTO getSupplierBySupplierName(String supplierName);

  SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierRequestDTO);
}
