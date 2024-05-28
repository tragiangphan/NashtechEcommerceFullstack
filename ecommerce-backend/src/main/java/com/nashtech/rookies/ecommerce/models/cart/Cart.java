package com.nashtech.rookies.ecommerce.models.cart;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.key.IdEntity;
import com.nashtech.rookies.ecommerce.models.user.User;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CARTS")
public class Cart extends IdEntity<Long> {
    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    private Long quantity;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    public Cart(User user, Long quantity) {
        this.user = user;
        this.quantity = quantity;
    }
}
