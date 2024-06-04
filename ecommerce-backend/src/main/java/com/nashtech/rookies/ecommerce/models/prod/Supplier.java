package com.nashtech.rookies.ecommerce.models.prod;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.key.AuditEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier extends AuditEntity<Long> {
  private String supplierName;
  private String phoneNo;
  private String email;
  private String address;
  private String street;
  private String ward;
  private String city;
  private String country;
  private String postalCode;
  private ActiveModeEnum activeMode = ActiveModeEnum.ACTIVE;

  @ManyToMany(mappedBy = "suppliers")
  Set<Product> products;
  
}
