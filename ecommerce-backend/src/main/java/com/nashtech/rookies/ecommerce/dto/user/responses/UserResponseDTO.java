package com.nashtech.rookies.ecommerce.dto.user.responses;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;

/**
 * UserResponseDTO
 */
public record UserResponseDTO(Long id,
    String firstName, String lastName,
    String email, String password,
    String phoneNo, ActiveModeEnum activeMode,
    Long roleId, Long inforId) {
}
