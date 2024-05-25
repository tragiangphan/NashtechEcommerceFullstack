package com.nashtech.rookies.ecommerce.dto.cart.responses;

import java.time.LocalDateTime;

public record CartItemResponseDTO(Long id,
    LocalDateTime createdOn, LocalDateTime lastUpdatedOn,
    Long quantity, Long cartId, Long productId) {

}
