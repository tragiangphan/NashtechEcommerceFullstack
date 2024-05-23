package com.nashtech.rookies.ecommerce.dto.prod.requests;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.FeatureModeEnum;

import jakarta.validation.constraints.NotBlank;

public record ProductRequestDTO(
    @NotBlank(message = "is required") String productName,
    String productDesc,
    String unit,
    int price,
    Long quantity,
    @NotBlank(message = "is required") FeatureModeEnum featureMode,
    @NotBlank(message = "is required") Long categoryId,
    @NotBlank(message = "is required") Set<Long> suppliers) {
}
