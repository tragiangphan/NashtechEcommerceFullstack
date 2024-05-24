package com.nashtech.rookies.ecommerce.models.prods;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.cart.CartItem;
import com.nashtech.rookies.ecommerce.models.constants.FeatureModeEnum;
import com.nashtech.rookies.ecommerce.models.key.AuditEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends AuditEntity<Long> {
  @Column(nullable = false)
  private String productName;
  private String productDesc;
  private String unit;
  private Long price;
  private long quantity;
  @Column(nullable = false)
  private FeatureModeEnum featureMode = FeatureModeEnum.FEATURED;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "products_suppliers", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "supplier_id"))
  Set<Supplier> suppliers;

  @OneToMany(mappedBy = "product")
  private Set<CartItem> cartItem;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @OneToMany(mappedBy = "product")
  private Set<Image> images;

  public Product(String productName, String productDesc, String unit, Long price, long quantity,
      FeatureModeEnum featureMode) {
    this.productName = productName;
    this.productDesc = productDesc;
    this.unit = unit;
    this.price = price;
    this.quantity = quantity;
    this.featureMode = featureMode;
  }
}
