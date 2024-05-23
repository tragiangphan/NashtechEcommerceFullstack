package com.nashtech.rookies.ecommerce.dto.user.requests;

import jakarta.validation.constraints.NotBlank;

public record InforRequestDTO(
    String address, 
    String street,
    String ward, 
    @NotBlank(message = "is required") String city,
    @NotBlank(message = "is required") String country, 
    String postalCode,
    @NotBlank(message = "is required") Long userId) {
}