package com.nashtech.rookies.ecommerce.dto.prod.requests;

import jakarta.validation.constraints.NotBlank;

public record ImageRequestDTO(
  String imageDesc, 
  @NotBlank(message = "is required") Long productId) {
} 