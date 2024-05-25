package com.nashtech.rookies.ecommerce.dto.prod.requests;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.FeatureModeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDTO(
    @NotBlank(message = "is required") String productName,
    String productDesc,
    String unit,
    Long price,
    Long quantity,
    @NotNull(message = "is required") FeatureModeEnum featureMode,
    @NotBlank(message = "is required") Long categoryId,
    @NotBlank(message = "is required") Set<Long> suppliers) {
}
