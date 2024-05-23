package com.nashtech.rookies.ecommerce.dto.prod.responses;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;

public record CategoryResponseDTO(
    Long id, String categoryName,
    String categoryDesc, ActiveModeEnum activeMode,
    Set<Long> products) {
}
