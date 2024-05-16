package com.nashtech.rookies.ecommerce.mappers.prod;

import org.mapstruct.Mapper;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
  SupplierRequestDTO toRequestDTO(Supplier supplier);
  SupplierResponseDTO toResponseDTO(Supplier supplier);

  Supplier toRequestEntity(SupplierRequestDTO supplierDTO);
  Supplier toResponseEntity(SupplierResponseDTO supplierDTO);
}
