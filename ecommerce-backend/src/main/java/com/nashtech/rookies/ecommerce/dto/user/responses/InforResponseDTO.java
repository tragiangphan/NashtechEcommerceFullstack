package com.nashtech.rookies.ecommerce.dto.user.responses;

public record InforResponseDTO(Long id,
    String address, String street,
    String ward, String district, String city,
    String country, String postalCode, Long userId) {
}
