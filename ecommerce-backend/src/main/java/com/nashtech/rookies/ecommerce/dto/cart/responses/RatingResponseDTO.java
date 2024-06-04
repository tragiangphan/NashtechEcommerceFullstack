package com.nashtech.rookies.ecommerce.dto.cart.responses;

import java.time.LocalDateTime;

public record RatingResponseDTO(Long id,
                                LocalDateTime createOn, LocalDateTime lastUpdatedOn,
                                Long rateScore, String comment,
                                Long productId, Long userId) {

}
