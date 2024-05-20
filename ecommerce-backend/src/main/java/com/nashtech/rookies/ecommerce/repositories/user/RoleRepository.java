package com.nashtech.rookies.ecommerce.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.user.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
