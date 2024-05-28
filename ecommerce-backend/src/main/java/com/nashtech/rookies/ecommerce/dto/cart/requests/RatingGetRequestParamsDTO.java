package com.nashtech.rookies.ecommerce.dto.cart.requests;

import org.springframework.data.domain.Sort;

public record RatingGetRequestParamsDTO(Long id, Long productId, Boolean average, Sort.Direction dir, Integer pageNum,
                                        Integer pageSize) {
}
