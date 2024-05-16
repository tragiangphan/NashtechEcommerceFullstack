package com.nashtech.rookies.ecommerce.dto.prod.requests;

public record SupplierRequestDTO(
    String supplierName, String phoneNo,
    String email, String address,
    String street, String ward,
    String city, String country,
    String postalCode, boolean isActive) {
}
