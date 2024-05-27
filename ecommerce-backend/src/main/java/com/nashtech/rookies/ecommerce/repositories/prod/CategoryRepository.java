package com.nashtech.rookies.ecommerce.repositories.prod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.prod.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  boolean existsById(Long id);
  boolean existsByCategoryName(String categoryName);
  Category findByCategoryName(String categoryName);
}
