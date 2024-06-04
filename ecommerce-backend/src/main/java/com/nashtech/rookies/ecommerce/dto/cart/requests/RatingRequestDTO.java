package com.nashtech.rookies.ecommerce.dto.cart.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RatingRequestDTO(
    // @Min(value = 1) @Max(5) 
    @Size(min = 0, max = 5)
    long rateScore, String comment,
    @NotBlank(message = "is required") Long productId) {
}
