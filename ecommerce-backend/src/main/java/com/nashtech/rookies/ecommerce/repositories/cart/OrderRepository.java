package com.nashtech.rookies.ecommerce.repositories.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.cart.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsById(Long id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM orders o WHERE o.user_id = :userId")
    List<Order> findAllByUserId(@Param("userId") Long userId);
}
