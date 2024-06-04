package com.nashtech.rookies.ecommerce.dto.prod.responses;

import java.util.List;

public record SupplierPaginationDTO(Integer totalPage, Long totalElement, Integer pageSize, Integer pageNum,
    List<SupplierResponseDTO> supplierResponseDTOs) {
}
