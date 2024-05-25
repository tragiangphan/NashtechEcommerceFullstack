package com.nashtech.rookies.ecommerce.dto.user.requests;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
    String firstName, 
    String lastName,
    @NotBlank(message = "is required") String email, 
    @NotBlank(message = "is required") String password,
    String phoneNo, 
    @NotNull(message = "is required") ActiveModeEnum activeMode, 
    @NotBlank(message = "is required") Long roleId) {
}
