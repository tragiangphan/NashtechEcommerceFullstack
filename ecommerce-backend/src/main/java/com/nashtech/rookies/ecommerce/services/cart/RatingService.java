package com.nashtech.rookies.ecommerce.services.cart;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.RatingResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Rating;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface RatingService extends CommonService<Rating, Long> {
  RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO);

  List<RatingResponseDTO> getRating();

  List<RatingResponseDTO> getRating(Long id);

  RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO);
}
