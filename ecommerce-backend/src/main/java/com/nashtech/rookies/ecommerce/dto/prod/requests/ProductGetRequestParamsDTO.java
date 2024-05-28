package com.nashtech.rookies.ecommerce.dto.prod.requests;

import org.springframework.data.domain.Sort;

public record ProductGetRequestParamsDTO(Long id,
                                         String productName, String categoryName,
                                         Long maxPrice, Long minPrice,
                                         Sort.Direction dir,
                                         Integer pageNum, Integer pageSize) {
}
