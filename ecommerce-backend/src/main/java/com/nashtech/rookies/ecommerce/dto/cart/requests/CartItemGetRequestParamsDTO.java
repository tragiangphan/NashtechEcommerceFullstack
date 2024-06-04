package com.nashtech.rookies.ecommerce.dto.cart.requests;

import org.springframework.data.domain.Sort;

public record CartItemGetRequestParamsDTO(Long id, Long userId,
                                          Sort.Direction direction, Integer pageNum, Integer pageSize) {
}
