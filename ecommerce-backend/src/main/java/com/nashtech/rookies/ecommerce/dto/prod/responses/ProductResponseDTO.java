package com.nashtech.rookies.ecommerce.dto.prod.responses;

public record ProductResponseDTO(Long id,
    String productName, String productDesc,
    String unit, int price, long quantity,
    boolean isFeatured, Long categoryId) {
}
