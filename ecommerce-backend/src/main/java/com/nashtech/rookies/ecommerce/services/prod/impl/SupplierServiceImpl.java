package com.nashtech.rookies.ecommerce.services.prod.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.models.prod.Supplier;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.SupplierService;


@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl extends CommonServiceImpl<Supplier, Long> implements SupplierService {

  private final SupplierRepository supplierRepository;

  SupplierServiceImpl(SupplierRepository supplierRepository) {
    super(supplierRepository);
    this.supplierRepository = supplierRepository;
  }
}
