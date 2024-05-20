package com.nashtech.rookies.ecommerce.dto.prod.requests;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
    @NotNull(message = "Category Name is required") 
    @NotBlank(message = "Category Name is required") 
    String categoryName,
    String categoryDesc,
    @NotNull(message = "Active Mode is required") ActiveModeEnum activeMode) {
}
