package com.nashtech.rookies.ecommerce.repositories.prod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.prod.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
