package com.nashtech.rookies.ecommerce.dto.cart.requests;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record OrderRequestDTO(
    LocalDateTime createdOn,
    LocalDateTime lastUpdatedOn,
    @NotBlank(message = "is required") Long userId) {

}
