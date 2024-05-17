package com.nashtech.rookies.ecommerce.repositories.prod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
