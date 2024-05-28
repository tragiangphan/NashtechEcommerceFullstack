package com.nashtech.rookies.ecommerce.models.user;

import com.nashtech.rookies.ecommerce.models.key.IdEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users_infor")
public class Infor extends IdEntity<Long> {
    private String address;
    private String street;
    private String ward;
    private String city;
    private String country;
    private String postalCode;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Infor(User user) {
        this.user = user;
    }
}
