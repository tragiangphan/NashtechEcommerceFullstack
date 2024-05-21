package com.nashtech.rookies.ecommerce.repositories.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.cart.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  boolean existsById(Long id);
}
