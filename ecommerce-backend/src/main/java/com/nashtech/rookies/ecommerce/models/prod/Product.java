package com.nashtech.rookies.ecommerce.models.prod;

import java.util.Set;

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
@Table(name = "PRODUCTS")
public class Product extends AuditEntity<Long> {
  @Column(nullable = false)
  private String productName;
  private String productDesc;
  private String unit;
  private int price;
  private long quantity;
  @Column(nullable = false)
  private boolean isFeatured;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
    name = "PRODUCTS_SUPPLIERS", 
    joinColumns = @JoinColumn(name = "PRODUCT_ID"), 
    inverseJoinColumns = @JoinColumn(name = "SUPPLIER_ID"))
  Set<Supplier> suppliers;

  @ManyToOne
  @JoinColumn(name="category_id", nullable=false)
  private Category category;

  @OneToMany(mappedBy = "product")
  private Set<Image> productImages;
}
