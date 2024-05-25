package com.nashtech.rookies.ecommerce.models.user;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.cart.Cart;
import com.nashtech.rookies.ecommerce.models.cart.Order;
import com.nashtech.rookies.ecommerce.models.cart.Rating;
import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.key.AuditEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditEntity<Long> {
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phoneNo;
    @Column(nullable = false)
    private ActiveModeEnum activeMode = ActiveModeEnum.ACTIVE;

    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Infor infor;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<Rating> ratings;
}
