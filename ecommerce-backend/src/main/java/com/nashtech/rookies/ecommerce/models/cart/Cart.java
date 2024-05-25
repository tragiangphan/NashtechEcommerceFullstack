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
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    private Long quantity;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    public Cart(User user) {
        this.user = user;
    }

    public void setQuantity() {
        this.quantity = (long) cartItems.size();
    }
}
