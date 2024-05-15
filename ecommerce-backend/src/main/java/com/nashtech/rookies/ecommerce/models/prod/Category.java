package com.nashtech.rookies.ecommerce.models.prod;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.key.IdEntity;

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
  private String categoryName;
  private String categoryDesc;
  private boolean isActive;

  @OneToMany(mappedBy = "category")
  private Set<Product> products;
}
