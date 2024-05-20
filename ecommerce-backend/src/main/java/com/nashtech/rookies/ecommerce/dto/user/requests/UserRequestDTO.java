package com.nashtech.rookies.ecommerce.dto.user.requests;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;

public record UserRequestDTO(
    String firstName, String lastName,
    String email, String password,
    String phoneNo, ActiveModeEnum activeMode, 
    Long roleId, Long inforId) {
}
