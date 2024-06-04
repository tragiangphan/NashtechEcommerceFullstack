package com.nashtech.rookies.ecommerce.dto.prod.requests;

import org.springframework.data.domain.Sort;

public record CategoryGetRequestParamsDTO(Long id, String categoryName, Sort.Direction dir,
    Integer pageNum, Integer pageSize) {
}
