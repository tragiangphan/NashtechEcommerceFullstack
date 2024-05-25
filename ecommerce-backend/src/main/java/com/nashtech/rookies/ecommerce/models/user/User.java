package com.nashtech.rookies.ecommerce.models.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditEntity<Long> implements UserDetails {
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    private String username;
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

    public User(String firstName, String lastName, String email, String password, String phoneNo, ActiveModeEnum activeMode, Role role, Infor infor, Cart cart, Set<Order> orders, Set<Rating> ratings) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = email.split("@")[0] + "_" + email.split("@")[1].split("\\.")[0];
        this.password = password;
        this.phoneNo = phoneNo;
        this.activeMode = activeMode;
        this.role = role;
        this.infor = infor;
        this.cart = cart;
        this.orders = orders;
        this.ratings = ratings;
    }

    public void setEmail(String email) {
        this.email = email;
        this.username = email.split("@")[0] + "_" + email.split("@")[1].split("\\.")[0];
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        if ( this.role != null ) {
            return Collections.singleton(new SimpleGrantedAuthority(this.role.getRoleName()));
        }
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }
}
