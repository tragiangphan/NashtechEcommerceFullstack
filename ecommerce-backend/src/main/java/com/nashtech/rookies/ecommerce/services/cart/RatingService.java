package com.nashtech.rookies.ecommerce.services.cart;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.PaginationRatingDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.RatingResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Rating;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.data.domain.Sort;

public interface RatingService extends CommonService<Rating, Long> {
    RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO);

    PaginationRatingDTO getRating(Sort.Direction dir, int pageNum, int pageSize);

    PaginationRatingDTO getRatingByProductId(Long productId, Sort.Direction dir, int pageNum, int pageSize);

    Double getAverageRatingByProductId(Long productId);

    RatingResponseDTO getRating(Long id);

    RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO);
}
