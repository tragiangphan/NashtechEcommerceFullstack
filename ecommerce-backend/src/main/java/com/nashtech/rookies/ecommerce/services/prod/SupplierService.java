package com.nashtech.rookies.ecommerce.services.prod;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface SupplierService extends CommonService<Supplier, Long> {
  SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO);

  List<SupplierResponseDTO> getSuppliers();

  List<SupplierResponseDTO> getSuppliers(Long id);

  SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierRequestDTO);
}
