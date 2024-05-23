package com.nashtech.rookies.ecommerce.dto.cart.requests;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record CartItemRequestDTO(
    LocalDateTime createdOn,
    LocalDateTime lastUpdatedOn,
    Long quantity,
    @NotBlank(message = "is required") Long cartId,
    Long orderId,
    @NotBlank(message = "is required") Long productId) {
}
