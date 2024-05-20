package com.nashtech.rookies.ecommerce.dto.user.requests;

/**
 * InforRequestDTO
 */
public record InforRequestDTO(
    String address, String street,
    String ward, String city,
    String country, String postalCode, Long userId) {
}