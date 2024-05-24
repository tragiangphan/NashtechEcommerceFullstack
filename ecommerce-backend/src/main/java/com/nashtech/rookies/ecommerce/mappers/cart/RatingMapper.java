package com.nashtech.rookies.ecommerce.mappers.cart;

import org.mapstruct.Mapper;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.RatingResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
  // @Mapping(target = "cartId", source = "Rating.cart.id")
  // @Mapping(target = "RatingId", source = "Rating.Rating.id")
  RatingRequestDTO toRequestDTO(Rating rating);

  // @Mapping(target = "cartId", source = "Rating.cart.id")
  // @Mapping(target = "RatingId", source = "Rating.Rating.id")
  RatingResponseDTO toResponseDTO(Rating rating);

  Rating toRequestEntity(RatingRequestDTO rating);
}
