package com.nashtech.rookies.ecommerce.repositories.prod;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.prods.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsById(Long id);
  Page<Product> findProductByProductNameLike(String productName, Pageable pageable);
}
