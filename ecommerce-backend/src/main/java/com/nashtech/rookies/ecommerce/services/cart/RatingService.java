package com.nashtech.rookies.ecommerce.services.cart;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.RatingResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Rating;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.http.ResponseEntity;

public interface RatingService extends CommonService<Rating, Long> {
    RatingResponseDTO createRating(String username, RatingRequestDTO ratingRequestDTO);

    ResponseEntity<?> handleGetRating(RatingGetRequestParamsDTO requestParamsDTO);

    RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO);
}
