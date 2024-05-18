package com.nashtech.rookies.ecommerce.models.prod;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.key.AuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SUPPLIERS")
public class Supplier extends AuditEntity<Long> {
  @Column(nullable = false)
  private String supplierName;
  private String phoneNo;
  private String email;
  private String address;
  private String street;
  private String ward;
  private String city;
  private String country;
  private String postalCode;
  @Column(nullable = false)
  private ActiveModeEnum activeMode = ActiveModeEnum.ACTIVE;

  @ManyToMany(mappedBy = "suppliers")
  Set<Product> products;
}
