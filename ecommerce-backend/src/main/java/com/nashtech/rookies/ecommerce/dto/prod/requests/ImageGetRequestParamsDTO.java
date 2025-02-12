package com.nashtech.rookies.ecommerce.dto.prod.requests;

import org.springframework.data.domain.Sort;

public record ImageGetRequestParamsDTO(
    Long id, Long productId, Sort.Direction dir,
    Integer pageNum, Integer pageSize) {
}
