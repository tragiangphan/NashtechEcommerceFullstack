package com.nashtech.rookies.ecommerce.repositories.cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.cart.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
  boolean existsById(Long id);
  boolean existsByProductIdAndUserId(Long productId, Long userId);

  @Query(value =
          "SELECT r.* " +
          "FROM ratings r " +
          "WHERE r.product_id = :productId",
          nativeQuery = true)
  Page<Rating> findByRatingByProductId(@Param("productId") Long productId, Pageable pageable);

  @Query(value =
          "SELECT AVG(r.rate_range) " +
          "FROM ratings r " +
          "JOIN products p " +
          "ON r.product_id = p.id " +
          "WHERE p.product_name LIKE :productName",
          nativeQuery = true)
  Double getAverageRatingByProductName(@Param("productName") String productName);
}
