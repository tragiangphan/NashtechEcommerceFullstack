package com.nashtech.rookies.ecommerce.dto.cart.requests;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;

public record CartRequestDTO(
    @NotBlank(message = "is required") Long userId,
    Set<Long> cartItem) {
}
