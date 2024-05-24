package com.nashtech.rookies.ecommerce.dto.prod.requests;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
    @NotBlank(message = "is required") String categoryName,
    String categoryDesc,
    @NotNull(message = "is required") ActiveModeEnum activeMode) {
}
