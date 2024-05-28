package com.nashtech.rookies.ecommerce.services.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.http.ResponseEntity;

public interface SupplierService extends CommonService<Supplier, Long> {
  SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO);

  ResponseEntity<?> handleGetSupplier(SupplierGetRequestParamsDTO requestParamsDTO);

  SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierRequestDTO);
}
