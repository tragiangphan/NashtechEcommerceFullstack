package com.nashtech.rookies.ecommerce.dto.prod.responses;

import java.util.List;

public record CategoryPaginationDTO(Integer totalPage, Long totalElement, Integer pageSize, Integer pageNum,
    List<CategoryResponseDTO> categoryResponseDTOs) {
}
