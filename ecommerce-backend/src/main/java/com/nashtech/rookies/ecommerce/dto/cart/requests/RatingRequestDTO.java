package com.nashtech.rookies.ecommerce.dto.cart.requests;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record RatingRequestDTO(
    Long rateRange, String rateDesc,
    @NotBlank(message = "is required") Long productId,
    @NotBlank(message = "is required") Long userId) {
}
