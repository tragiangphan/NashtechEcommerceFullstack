package com.nashtech.rookies.ecommerce.repositories.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.cart.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
  boolean existsById(Long id);
}
