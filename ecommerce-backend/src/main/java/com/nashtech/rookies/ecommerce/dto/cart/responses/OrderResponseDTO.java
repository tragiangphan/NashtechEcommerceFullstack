package com.nashtech.rookies.ecommerce.dto.cart.responses;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDTO(Long id,
                               LocalDateTime createOn, LocalDateTime lastUpdateOn,
                               Set<Long> cartItems, Long userId) {

}
