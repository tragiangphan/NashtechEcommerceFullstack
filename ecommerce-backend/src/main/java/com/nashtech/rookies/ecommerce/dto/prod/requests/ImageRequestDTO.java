package com.nashtech.rookies.ecommerce.dto.prod.requests;

import jakarta.validation.constraints.NotBlank;

public record ImageRequestDTO(
  @NotBlank(message = "is required") String imageLink, 
  String imageDesc, 
  @NotBlank(message = "is required") Long productId) {
} 