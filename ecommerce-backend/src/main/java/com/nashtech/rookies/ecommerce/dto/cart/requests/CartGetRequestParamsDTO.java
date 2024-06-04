package com.nashtech.rookies.ecommerce.dto.cart.requests;

import org.springframework.data.domain.Sort;

public record CartGetRequestParamsDTO(Long id, Long userId) {
}
