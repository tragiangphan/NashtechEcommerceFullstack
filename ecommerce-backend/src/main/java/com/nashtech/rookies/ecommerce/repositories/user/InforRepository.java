package com.nashtech.rookies.ecommerce.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.user.Infor;

@Repository
public interface InforRepository extends JpaRepository<Infor, Long> {
  boolean existsById(Long id);
}
