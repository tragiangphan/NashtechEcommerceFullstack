package com.nashtech.rookies.ecommerce.dto.prod.requests;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;

public record CategoryRequestDTO(String categoryName, String categoryDesc, ActiveModeEnum activeMode) {
}
