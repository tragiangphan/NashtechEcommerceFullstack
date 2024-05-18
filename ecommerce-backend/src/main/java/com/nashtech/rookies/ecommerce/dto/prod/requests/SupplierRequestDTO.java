package com.nashtech.rookies.ecommerce.dto.prod.requests;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;

public record SupplierRequestDTO(
    String supplierName, String phoneNo,
    String email, String address,
    String street, String ward,
    String city, String country,
    String postalCode, ActiveModeEnum activeMode) {
}
