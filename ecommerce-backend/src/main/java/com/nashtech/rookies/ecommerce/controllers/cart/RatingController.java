package com.nashtech.rookies.ecommerce.controllers.cart;

import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingGetRequestParamsDTO;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.cart.requests.RatingRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.RatingResponseDTO;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.services.cart.RatingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/ratings")
@Slf4j
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping()
    public ResponseEntity<RatingResponseDTO> createRatings(@AuthenticationPrincipal User user,
            @RequestBody RatingRequestDTO ratingRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return ResponseEntity.ok(ratingService.createRating(currentPrincipalName, ratingRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getRating(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "average", required = false) Boolean average,
            @RequestParam(name = "direction") Sort.Direction dir,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "pageSize") Integer pageSize) {
        return ratingService.handleGetRating(new RatingGetRequestParamsDTO(id, productId, average, dir, pageNum, pageSize));
    }

    @PutMapping()
    public ResponseEntity<RatingResponseDTO> updateRating(@RequestParam(name = "id", required = true) Long id,
            @RequestBody RatingRequestDTO ratingRequestDTO) {
        return ResponseEntity.ok(ratingService.updateRating(id, ratingRequestDTO));
    }
}
