package com.nashtech.rookies.ecommerce.dto.prod.requests;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
    @NotBlank(message = "is required") String categoryName,
    String categoryDesc,
    @NotBlank(message = "is required") ActiveModeEnum activeMode) {
}
