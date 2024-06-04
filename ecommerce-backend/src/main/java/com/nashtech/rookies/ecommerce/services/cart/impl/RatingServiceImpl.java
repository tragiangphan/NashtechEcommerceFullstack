package com.nashtech.rookies.ecommerce.services.cart.impl;

import java.util.ArrayList;
import java.util.List;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.PaginationRatingDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.RatingResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.models.cart.Rating;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.repositories.cart.RatingRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.cart.RatingService;

@Service
@Transactional(readOnly = true)
public class RatingServiceImpl extends CommonServiceImpl<Rating, Long> implements RatingService {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ProductRepository productRepository,
            UserRepository userRepository) {
        super(ratingRepository);
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public RatingResponseDTO createRating(String username, RatingRequestDTO ratingRequestDTO) {
        if (userRepository.existsUserByUsername(username)) {
            User user = userRepository.findOneByUsername(username).get();
            if (!ratingRepository.existsByProductIdAndUserId(ratingRequestDTO.productId(), user.getId())) {
                if (productRepository.existsById(ratingRequestDTO.productId())) {
                    Rating rating = new Rating();
                    rating.setProduct(productRepository.findById(ratingRequestDTO.productId()).get());
                    rating.setRateScore(ratingRequestDTO.rateScore());
                    rating.setComment(ratingRequestDTO.comment());
                    rating.setUser(userRepository.findById(user.getId()).get());
                    rating = ratingRepository.saveAndFlush(rating);
                    return new RatingResponseDTO(rating.getId(), rating.getCreatedOn(), rating.getLastUpdatedOn(),
                            rating.getRateScore(),
                            rating.getComment(), rating.getProduct().getId(), rating.getUser().getId());
                } else {
                    throw new NotFoundException("Not found a Product with an id: " + ratingRequestDTO.productId());
                }
            } else {
                throw new ResourceConflictException("This Product has been rated by User with an id: " + user.getId());
            }
        } else {
            throw new NotFoundException("Not found an User with an username: " + username);
        }

    }

    @Override
    public ResponseEntity<?> handleGetRating(RatingGetRequestParamsDTO requestParamsDTO) {
        RatingResponseDTO ratingResponseDTO;
        PaginationRatingDTO ratingResponseDTOs;
        Double ratingAvg;

        if (requestParamsDTO.id() != null) {
            ratingResponseDTO = getRating(requestParamsDTO.id());
            return ResponseEntity.ok(ratingResponseDTO);
        } else if (Boolean.TRUE.equals(requestParamsDTO.average())) {
            ratingAvg = getAverageRatingByProductId(requestParamsDTO.productId(), requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(ratingAvg);
        } else if (requestParamsDTO.productId() != null) {
            ratingResponseDTOs = getRatingByProductId(requestParamsDTO.productId(), requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(ratingResponseDTOs);
        } else {
            ratingResponseDTOs = getRating(requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(ratingResponseDTOs);
        }
    }

    public PaginationRatingDTO getRating(Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Rating> ratings = ratingRepository.findAll(pageable);
        List<RatingResponseDTO> ratingResponseDTOs = new ArrayList<>();
        ratings.forEach(rating -> ratingResponseDTOs.add(new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                rating.getLastUpdatedOn(), rating.getRateScore(), rating.getComment(),
                rating.getProduct().getId(), rating.getUser().getId())));
        return new PaginationRatingDTO(ratings.getTotalPages(), ratings.getTotalElements(), ratings.getSize(),
                ratings.getNumber(), ratingResponseDTOs);
    }

    public PaginationRatingDTO getRatingByProductId(Long productId, Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Rating> ratings = ratingRepository.findByRatingByProductId(productId, pageable);
        List<RatingResponseDTO> ratingResponseDTOs = new ArrayList<>();
        ratings.forEach(rating -> ratingResponseDTOs.add(new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                rating.getLastUpdatedOn(), rating.getRateScore(), rating.getComment(),
                rating.getProduct().getId(), rating.getUser().getId())));
        return new PaginationRatingDTO(ratings.getTotalPages(), ratings.getTotalElements(), ratings.getSize(),
                ratings.getNumber(), ratingResponseDTOs);
    }

    public Double getAverageRatingByProductId(Long productId, Sort.Direction dir, int pageNum, int pageSize) {
        if (productRepository.existsById(productId)) {
            Sort sort = Sort.by(dir, "id");
            Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
            Page<Rating> ratings = ratingRepository.findByRatingByProductId(productId, pageable);
            if (ratings.getTotalElements() > 0) {
                return ratingRepository.getAverageRatingByProductId(productId);
            } else {
                return 0.0;
            }
        } else {
            throw new NotFoundException("Not found a Product with an id: " + productId);
        }
    }

    public RatingResponseDTO getRating(Long id) {
        if (ratingRepository.existsById(id)) {
            Rating rating = ratingRepository.findById(id).get();
            return new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                    rating.getLastUpdatedOn(), rating.getRateScore(), rating.getComment(),
                    rating.getProduct().getId(), rating.getUser().getId());
        } else {
            throw new NotFoundException("Not found a Rating with an id: " + id);
        }
    }

    @Override
    @Transactional
    public RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO) {
        if (ratingRepository.existsById(id)) {
            Rating rating = ratingRepository.findById(id).get();
            rating.setRateScore(ratingRequestDTO.rateScore());
            rating.setComment(ratingRequestDTO.comment());
            rating = ratingRepository.saveAndFlush(rating);
            return new RatingResponseDTO(rating.getId(), rating.getCreatedOn(),
                    rating.getLastUpdatedOn(), rating.getRateScore(), rating.getComment(),
                    rating.getProduct().getId(), rating.getUser().getId());
        } else {
            throw new NotFoundException("Not found Rating with an id: " + id);
        }
    }
}
