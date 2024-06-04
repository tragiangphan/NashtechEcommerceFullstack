package com.nashtech.rookies.ecommerce.dto.prod.responses;

import java.util.List;

public record ProductPaginationDTO(
    Integer totalPage, Long totalElement,
    Integer pageSize, Integer pageNum,
    List<ProductResponseDTO> products) {
}
