package com.nashtech.rookies.ecommerce.dto.cart.responses;

import java.util.List;

public record PaginationRatingDTO(Integer totalPage, Long totalElement, Integer pageSize, Integer pageNum,
                                  List<RatingResponseDTO> ratings) {
}
