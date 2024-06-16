package com.nashtech.rookies.ecommerce.dto.cart.responses;

import java.time.LocalDateTime;

public record OrderResponseDTO(Long id,
                               LocalDateTime createOn, LocalDateTime lastUpdateOn,
                               Long quantity, Long productId, Long userId) {

}
