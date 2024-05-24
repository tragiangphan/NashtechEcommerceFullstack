package com.nashtech.rookies.ecommerce.models.cart;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.key.AuditEntity;
import com.nashtech.rookies.ecommerce.models.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AuditEntity<Long> {
  @OneToMany(mappedBy = "order")
  private Set<CartItem> cartItems ;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
