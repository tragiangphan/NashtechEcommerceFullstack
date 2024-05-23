package com.nashtech.rookies.ecommerce.dto.cart.requests;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record RatingRequestDTO(
    LocalDateTime createOn, LocalDateTime lastUpdatedOn,
    Long rateRange, String rateDesc,
    @NotBlank(message = "is required") Long cartItemId,
    @NotBlank(message = "is required") Long userId) {

}
