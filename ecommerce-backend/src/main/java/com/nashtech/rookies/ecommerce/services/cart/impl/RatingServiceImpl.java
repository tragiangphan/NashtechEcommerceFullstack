package com.nashtech.rookies.ecommerce.services.cart.impl;

import java.util.ArrayList;
import java.util.List;

import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.repositories.cart.CartItemRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.RatingResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.models.cart.Rating;
import com.nashtech.rookies.ecommerce.repositories.cart.RatingRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.cart.RatingService;

@Service
@Transactional(readOnly = true)
public class RatingServiceImpl extends CommonServiceImpl<Rating, Long> implements RatingService {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ProductRepository productRepository, UserRepository userRepository) {
        super(ratingRepository);
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO) {
        if (!ratingRepository.existsByProductIdAndUserId(ratingRequestDTO.productId(), ratingRequestDTO.userId())) {
            if (productRepository.existsById(ratingRequestDTO.productId())) {
                if (userRepository.existsById(ratingRequestDTO.userId())) {
                    Rating rating = new Rating();
                    rating.setProduct(productRepository.findById(ratingRequestDTO.productId()).get());
                    rating.setRateRange(ratingRequestDTO.rateRange());
                    rating.setRateDesc(ratingRequestDTO.rateDesc());
                    rating.setUser(userRepository.findById(ratingRequestDTO.userId()).get());
                    rating = ratingRepository.saveAndFlush(rating);
                    return new RatingResponseDTO(rating.getId(), rating.getCreatedOn(), rating.getLastUpdatedOn(), rating.getRateRange(),
                            rating.getRateDesc(), rating.getProduct().getId(), rating.getUser().getId());
                } else {
                    throw new NotFoundException("Not found an User with an id: " + ratingRequestDTO.userId());
                }
            } else {
                throw new NotFoundException("Not found a Product with an id: " + ratingRequestDTO.productId());
            }
        } else {
            throw new ResourceConflictException("This Product has been rated by User with an id: " + ratingRequestDTO.userId());
        }
    }

    @Override
    public List<RatingResponseDTO> getRating() {
        var ratings = ratingRepository.findAll();
        List<RatingResponseDTO> ratingResponseDTOs = new ArrayList<>();
        ratings.forEach(rating -> ratingResponseDTOs.add(new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                rating.getLastUpdatedOn(), rating.getRateRange(), rating.getRateDesc(),
                rating.getProduct().getId(), rating.getUser().getId())));
        return ratingResponseDTOs;
    }

    @Override
    public List<RatingResponseDTO> getRating(Long id) {
        if (ratingRepository.existsById(id)) {
            Rating rating = ratingRepository.findById(id).get();
            List<RatingResponseDTO> ratingResponseDTOs = new ArrayList<>();
            ratingResponseDTOs.add(new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                    rating.getLastUpdatedOn(), rating.getRateRange(), rating.getRateDesc(),
                    rating.getProduct().getId(), rating.getUser().getId()));
            return ratingResponseDTOs;
        } else {
            throw new NotFoundException("Not found a Rating with an id: " + id);
        }
    }

    @Override
    @Transactional
    public RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO) {
        if (ratingRepository.existsById(id)) {
            Rating rating = ratingRepository.findById(id).get();
            rating.setRateRange(ratingRequestDTO.rateRange());
            rating.setRateDesc(ratingRequestDTO.rateDesc());
            rating = ratingRepository.saveAndFlush(rating);
            return new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                    rating.getLastUpdatedOn(), rating.getRateRange(), rating.getRateDesc(),
                    rating.getProduct().getId(), rating.getUser().getId());
        } else {
            throw new NotFoundException("Not found Rating with an id: " + id);
        }
    }
}
