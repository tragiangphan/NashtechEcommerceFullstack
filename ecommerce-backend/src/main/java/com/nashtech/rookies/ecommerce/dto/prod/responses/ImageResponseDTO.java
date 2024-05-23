package com.nashtech.rookies.ecommerce.dto.prod.responses;

public record ImageResponseDTO(Long id,
    String imageLink, String imageDesc,
    Long productId) {
}
