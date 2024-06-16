package com.nashtech.rookies.ecommerce.dto.prod.responses;

import java.time.LocalDateTime;
import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.FeatureModeEnum;

public record ProductResponseDTO(Long id,
    String productName, String productDesc,
    String unit, Long price, Long quantity,
    FeatureModeEnum featureMode,
    Long categoryId, LocalDateTime createdOn,
    LocalDateTime lastUpdatedOn, Set<Long> suppliers,
    Set<Long> images) {
}
