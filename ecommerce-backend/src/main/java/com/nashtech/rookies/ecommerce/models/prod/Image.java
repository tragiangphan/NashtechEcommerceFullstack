package com.nashtech.rookies.ecommerce.models.prod;

import com.nashtech.rookies.ecommerce.models.key.IdEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "IMAGES")
public class Image extends IdEntity<Long> {
  @Column(nullable = false)
  private String imageLink;
  private String imageDesc;

  @ManyToOne()
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;
}
