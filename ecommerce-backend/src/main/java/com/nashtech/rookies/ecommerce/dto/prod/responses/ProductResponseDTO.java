package com.nashtech.rookies.ecommerce.dto.prod.responses;

import com.nashtech.rookies.ecommerce.models.enums.FeatureModeEnum;

public record ProductResponseDTO(Long id,
    String productName, String productDesc,
    String unit, int price, long quantity,
    FeatureModeEnum featureMode, Long categoryId) {
}
