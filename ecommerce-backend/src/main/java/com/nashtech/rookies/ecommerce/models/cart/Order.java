package com.nashtech.rookies.ecommerce.models.cart;

import com.nashtech.rookies.ecommerce.models.key.AuditEntity;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.models.user.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AuditEntity<Long> {
  private Long quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @OneToOne(mappedBy = "order")
  private CartItem cartItem;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Order(CartItem cartItem, User user) {
    this.cartItem = cartItem;
    this.quantity = cartItem.getQuantity();
    this.product = cartItem.getProduct();
    this.user = user;
  }
}
