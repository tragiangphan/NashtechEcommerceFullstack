package com.nashtech.rookies.ecommerce.dto.cart.requests;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record CartItemRequestDTO(
    Long quantity,
    @NotBlank(message = "is required") Long cartId,
    @NotBlank(message = "is required") Long productId) {
}
