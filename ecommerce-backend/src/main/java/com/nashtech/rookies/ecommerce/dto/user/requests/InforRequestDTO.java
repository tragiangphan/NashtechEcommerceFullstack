package com.nashtech.rookies.ecommerce.dto.user.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InforRequestDTO(
    String address, 
    String street,
    String ward, 
    @NotBlank(message = "is required") String city,
    @NotBlank(message = "is required") String country, 
    String postalCode,
    @NotNull(message = "must not be null") Long userId) {
}