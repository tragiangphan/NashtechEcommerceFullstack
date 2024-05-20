package com.nashtech.rookies.ecommerce.models.user;

import com.nashtech.rookies.ecommerce.models.key.IdEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users_info")
public class Infor extends IdEntity<Long> {
  private String address;
  private String street;
  private String ward;
  private String city;
  private String country;
  private String postalCode;

  @OneToOne(mappedBy = "infor", cascade = CascadeType.ALL)
  private User user;
}
