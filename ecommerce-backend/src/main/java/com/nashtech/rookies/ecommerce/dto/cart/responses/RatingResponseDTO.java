package com.nashtech.rookies.ecommerce.dto.cart.responses;

import java.time.LocalDateTime;

public record RatingResponseDTO(Long id,
                                LocalDateTime createOn, LocalDateTime lastUpdatedOn,
                                Long rateRange, String rateDesc,
                                Long productId, Long userId) {

}
