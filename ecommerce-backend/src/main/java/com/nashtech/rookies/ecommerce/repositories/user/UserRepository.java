package com.nashtech.rookies.ecommerce.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsById(Long id);
}
