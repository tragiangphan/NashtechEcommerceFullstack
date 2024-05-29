package com.nashtech.rookies.ecommerce.repositories.prod;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.rookies.ecommerce.models.prod.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
  boolean existsById(Long id);

  @Query(nativeQuery = true,
  value = "SELECT * FROM images i WHERE product_id = :productId")
  List<Image> findAllByProductId(@Param("productId") Long productId);
}
