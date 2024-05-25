package com.nashtech.rookies.ecommerce.dto.prod.requests;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SupplierRequestDTO(
    @NotBlank(message = "is required") String supplierName,
    String phoneNo,
    @NotBlank(message = "is required") String email,
    String address, String street,
    String ward, String city,
    String country, String postalCode,
    @NotNull(message = "is required") ActiveModeEnum activeMode,
    Set<Long> products) {
} 
