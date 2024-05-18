package com.nashtech.rookies.ecommerce.dto.prod.requests;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.enums.FeatureModeEnum;

public record ProductRequestDTO(
    String productName, String productDesc,
    String unit, int price, long quantity,
    FeatureModeEnum featureMode, Long categoryId, 
    Set<Long> suppliers) {
}
