package com.nashtech.rookies.ecommerce.dto.prod.responses;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;

public record SupplierResponseDTO(Long id,
    String supplierName, String phoneNo,
    String email, String address,
    String street, String ward,
    String city, String country,
    String postalCode, ActiveModeEnum activeMode,
    Set<Long> products) {
}
