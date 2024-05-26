package com.nashtech.rookies.ecommerce.controllers.cart;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import com.nashtech.rookies.ecommerce.services.cart.RatingService;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/ratings")
public class RatingController {
  private final RatingService ratingService;

  public RatingController(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @PostMapping()
  public ResponseEntity<RatingResponseDTO> createOrder(@RequestBody RatingRequestDTO ratingRequestDTO) {
    return ResponseEntity.ok(ratingService.createRating(ratingRequestDTO));
  }

  @GetMapping()
  public ResponseEntity<List<RatingResponseDTO>> getRating(@RequestParam(name = "id", required = false) Long id) {
    List<RatingResponseDTO> ratingResponseDTOs;

    if (id != null) {
      ratingResponseDTOs = ratingService.getRating(id);
    } else {
      ratingResponseDTOs = ratingService.getRating();
    }
    return ResponseEntity.ok(ratingResponseDTOs);
  }

  @PutMapping()
  public ResponseEntity<RatingResponseDTO> updateRating(@RequestParam(name = "id", required = true) Long id,
      @RequestBody RatingRequestDTO ratingRequestDTO) {
    return ResponseEntity.ok(ratingService.updateRating(id, ratingRequestDTO));
  }
}