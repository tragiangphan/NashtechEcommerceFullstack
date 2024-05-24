package com.nashtech.rookies.ecommerce.dto.cart.responses;

import java.util.Set;

public record CartResponseDTO(Long id, Long quantity, Long userId, Set<Long> cartItems) {
}
