package com.nashtech.rookies.ecommerce.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByEmail(String email);
}
