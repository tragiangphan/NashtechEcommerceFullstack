package com.nashtech.rookies.ecommerce.models.prod;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.key.IdEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CATEGORIES")
public class Category extends IdEntity<Long> {
  @Column(nullable = false)
  private String categoryName;
  private String categoryDesc;
  @Column(nullable = false)
  private ActiveModeEnum activeMode = ActiveModeEnum.ACTIVE;

  @OneToMany(mappedBy = "category")
  private Set<Product> products;
}
