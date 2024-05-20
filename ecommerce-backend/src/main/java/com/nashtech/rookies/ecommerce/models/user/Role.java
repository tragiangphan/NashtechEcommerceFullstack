package com.nashtech.rookies.ecommerce.models.user;

import java.util.Set;

import com.nashtech.rookies.ecommerce.models.key.IdEntity;

import jakarta.persistence.CascadeType;
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
@Table(name = "roles")
public class Role extends IdEntity<Long> {
  @Column(nullable = false)
  private String roleName;
  private String roleDesc;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
  private Set<User> users;
}
