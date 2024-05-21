package com.nashtech.rookies.ecommerce.repositories.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.cart.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  boolean existsById(Long id);
}
