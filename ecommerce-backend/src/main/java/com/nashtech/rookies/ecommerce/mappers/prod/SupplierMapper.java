package com.nashtech.rookies.ecommerce.mappers.prod;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.models.prods.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
  @Mapping(target = "products", ignore = true)
  SupplierRequestDTO toRequestDTO(Supplier supplier);
 
  @Mapping(target = "products", ignore = true)
  SupplierResponseDTO toResponseDTO(Supplier supplier);

  @Mapping(target = "products", ignore = true)
  Supplier toRequestEntity(SupplierRequestDTO supplierDTO);

  @Mapping(target = "products", ignore = true)
  Supplier toResponseEntity(SupplierResponseDTO supplierDTO);
}
