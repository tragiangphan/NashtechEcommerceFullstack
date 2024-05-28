package com.nashtech.rookies.ecommerce.repositories.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.cart.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsById(Long id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM carts c WHERE c.user_id = :user_id")
    Cart findByUserId(Long userId);
}
