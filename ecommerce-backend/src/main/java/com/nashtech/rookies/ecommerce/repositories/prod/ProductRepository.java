package com.nashtech.rookies.ecommerce.repositories.prod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.prod.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsById(Long id);
}
