package com.nashtech.rookies.ecommerce.repositories.cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.cart.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean existsById(Long id);

    @Query(value = "SELECT * " +
            "FROM cart_items i " +
            "WHERE i.cart_id = :userId",
            nativeQuery = true)
    Page<CartItem> findByUserId(Long userId, Pageable pageable);
}
