package com.nashtech.rookies.ecommerce.services.cart.impl;

import java.util.ArrayList;
import java.util.List;

import com.nashtech.rookies.ecommerce.exceptions.RequirementNotFoundException;
import com.nashtech.rookies.ecommerce.models.cart.CartItem;
import com.nashtech.rookies.ecommerce.repositories.cart.CartItemRepository;
import com.nashtech.rookies.ecommerce.repositories.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.RatingResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.cart.RatingMapper;
import com.nashtech.rookies.ecommerce.models.cart.Rating;
import com.nashtech.rookies.ecommerce.repositories.cart.RatingRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.cart.RatingService;

@Service
@Transactional(readOnly = true)
public class RatingServiceImpl extends CommonServiceImpl<Rating, Long> implements RatingService {
    private final RatingRepository ratingRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        super(ratingRepository);
        this.ratingRepository = ratingRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO) {
        if (cartItemRepository.existsById(ratingRequestDTO.cartItemId())) {
            Rating rating = new Rating();
            rating.setCartItem(cartItemRepository.findById(ratingRequestDTO.cartItemId()).get());
            if (rating.getCartItem().getOrder() != null) {
                rating.setRateRange(ratingRequestDTO.rateRange());
                rating.setRateDesc(ratingRequestDTO.rateDesc());
                rating.setUser(userRepository.findById(ratingRequestDTO.userId()).get());
                rating = ratingRepository.saveAndFlush(rating);
                return new RatingResponseDTO(rating.getId(), rating.getCreatedOn(), rating.getLastUpdatedOn(), rating.getRateRange(),
                        rating.getRateDesc(), rating.getCartItem().getId(), rating.getUser().getId());

            } else throw new RequirementNotFoundException("This Cart Item has not been purchased yet");
        } else {
            throw new ResourceNotFoundException("Not found a Cart Item with an id: " + ratingRequestDTO.cartItemId());
        }
    }

    @Override
    public List<RatingResponseDTO> getRating() {
        var ratings = ratingRepository.findAll();
        List<RatingResponseDTO> ratingResponseDTOs = new ArrayList<>();
        ratings.forEach(rating -> ratingResponseDTOs.add(new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                rating.getLastUpdatedOn(), rating.getRateRange(), rating.getRateDesc(),
                rating.getCartItem().getId(), rating.getUser().getId())));
        return ratingResponseDTOs;
    }

    @Override
    public List<RatingResponseDTO> getRating(Long id) {
        if (ratingRepository.existsById(id)) {
            Rating rating = ratingRepository.findById(id).get();
            List<RatingResponseDTO> ratingResponseDTOs = new ArrayList<>();
            ratingResponseDTOs.add(new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                    rating.getLastUpdatedOn(), rating.getRateRange(), rating.getRateDesc(),
                    rating.getCartItem().getId(), rating.getUser().getId()));
            return ratingResponseDTOs;
        } else {
            throw new ResourceNotFoundException("Not found a Rating with an id: " + id);
        }
    }

    @Override
    @Transactional
    public RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO) {
        if (ratingRepository.existsById(id)) {
            Rating rating = ratingRepository.findById(id).get();
            rating.setRateRange(ratingRequestDTO.rateRange());
            rating.setRateDesc(ratingRequestDTO.rateDesc());
            // NOT HAVE MODIFIED PERMISSION
            // rating.setUser(userRepository.findById(ratingRequestDTO.userId()).get());
            // rating.setCartItem(cartItemRepository.findById(ratingRequestDTO.cartItemId()).get());
            rating = ratingRepository.saveAndFlush(rating);
            return new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                    rating.getLastUpdatedOn(), rating.getRateRange(), rating.getRateDesc(),
                    rating.getCartItem().getId(), rating.getUser().getId());
        } else {
            throw new ResourceNotFoundException("Not found Rating with an id: " + id);
        }
    }
}
