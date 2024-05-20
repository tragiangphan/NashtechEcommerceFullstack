package com.nashtech.rookies.ecommerce.dto.user.responses;

/**
 * UserRequestDTO
 */
public record InforResponseDTO(Long id,
    String address, String street,
    String ward, String city,
    String country, String postalCode, Long userId) {
}
