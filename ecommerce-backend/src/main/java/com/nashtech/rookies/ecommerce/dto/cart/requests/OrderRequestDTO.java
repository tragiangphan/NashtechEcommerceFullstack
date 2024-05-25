package com.nashtech.rookies.ecommerce.dto.cart.requests;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;

public record OrderRequestDTO(
        @NotBlank(message = "is required") Long userId,
        @NotBlank(message = "is required") Set<Long> cartItems) {

}
