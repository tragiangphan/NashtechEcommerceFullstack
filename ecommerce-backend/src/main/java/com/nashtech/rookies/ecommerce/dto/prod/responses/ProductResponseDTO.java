package com.nashtech.rookies.ecommerce.dto.prod.responses;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.FeatureModeEnum;

public record ProductResponseDTO(Long id,
    String productName, String productDesc,
    String unit, int price, Long quantity,
    FeatureModeEnum featureMode, 
    Long categoryId, Set<Long> suppliers) {
}
