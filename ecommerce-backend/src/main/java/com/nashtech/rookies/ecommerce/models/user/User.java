package com.nashtech.rookies.ecommerce.models.user;

import com.nashtech.rookies.ecommerce.models.enums.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.models.key.AuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User extends AuditEntity<Long> {
  private String firstName;
  private String lastName;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  private String phoneNo;
  @Column(nullable = false)
  private ActiveModeEnum activeMode = ActiveModeEnum.ACTIVE;

  @ManyToOne()
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @OneToOne
  @JoinColumn(name = "infor_id", nullable = true)
  private Infor infor;
}
