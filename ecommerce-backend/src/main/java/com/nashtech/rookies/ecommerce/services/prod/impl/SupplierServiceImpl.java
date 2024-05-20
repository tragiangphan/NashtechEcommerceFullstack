package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.SupplierMapper;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.SupplierService;

@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl extends CommonServiceImpl<Supplier, Long> implements SupplierService {
  private final SupplierRepository supplierRepository;
  private final SupplierMapper supplierMapper;

  SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
    super(supplierRepository);
    this.supplierRepository = supplierRepository;
    this.supplierMapper = supplierMapper;
  }

  @Override
  @Transactional
  public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO) {
    Supplier supplier = supplierRepository.saveAndFlush(supplierMapper.toRequestEntity(supplierRequestDTO));
    return supplierMapper.toResponseDTO(supplier);
  }

  @Override
  public List<SupplierResponseDTO> getSuppliers() {
    var suppliers = supplierRepository.findAll();
    List<SupplierResponseDTO> supplierResponseDTOs = new ArrayList<>();
    suppliers.forEach(supplier -> supplierResponseDTOs.add(supplierMapper.toResponseDTO(supplier)));
    return supplierResponseDTOs;
  }

  @Override
  public List<SupplierResponseDTO> getSuppliers(Long id) {
    List<SupplierResponseDTO> supplierResponseDTOs = new ArrayList<>();
    Supplier supplier = supplierRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Supplier with an id: " + id));
    supplierResponseDTOs.add(supplierMapper.toResponseDTO(supplier));
    return supplierResponseDTOs;
  }

  @Override
  @Transactional
  public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierRequestDTO) {
    Supplier supplier = supplierRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Supplier with an id: " + id));
    supplier.setSupplierName(supplierRequestDTO.supplierName());
    supplier.setPhoneNo(supplierRequestDTO.phoneNo());
    supplier.setEmail(supplierRequestDTO.email());
    supplier.setAddress(supplierRequestDTO.address());
    supplier.setStreet(supplierRequestDTO.street());
    supplier.setWard(supplierRequestDTO.ward());
    supplier.setCity(supplierRequestDTO.city());
    supplier.setCountry(supplierRequestDTO.country());
    supplier.setPostalCode(supplierRequestDTO.postalCode());
    supplier.setActiveMode(supplierRequestDTO.activeMode());
    supplier = supplierRepository.saveAndFlush(supplier);
    return supplierMapper.toResponseDTO(supplier);
  }
}
