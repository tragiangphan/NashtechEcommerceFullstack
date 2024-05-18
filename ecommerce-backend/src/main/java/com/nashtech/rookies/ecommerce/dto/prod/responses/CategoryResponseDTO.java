package com.nashtech.rookies.ecommerce.dto.prod.responses;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;

public record CategoryResponseDTO(
  Long id, String categoryName, 
  String categoryDesc, ActiveModeEnum activeMode) {
}
