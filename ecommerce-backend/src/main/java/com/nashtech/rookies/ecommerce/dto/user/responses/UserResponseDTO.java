package com.nashtech.rookies.ecommerce.dto.user.responses;

import java.time.LocalDateTime;
import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;

public record UserResponseDTO(Long id,
    String firstName, String lastName,
    String email, String username, String password,
    String phoneNo, ActiveModeEnum activeMode,
    Long roleId, Long inforId, Long cartId,
    LocalDateTime createdOn, LocalDateTime lastUpdatedOn,
    Set<Long> orders, Set<Long> ratings) {
}
