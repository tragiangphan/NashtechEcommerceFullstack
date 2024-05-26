package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.prods.Category;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.SupplierMapper;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.models.prods.Supplier;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.SupplierService;

@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl extends CommonServiceImpl<Supplier, Long> implements SupplierService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final CategoryRepository categoryRepository;

    SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper,
                        ProductRepository productRepository, CategoryRepository categoryRepository) {
        super(supplierRepository);
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO) {
        if (supplierRepository.existsBySupplierName(supplierRequestDTO.supplierName())) {
            Supplier supplier = supplierRepository.findBySupplierName(supplierRequestDTO.supplierName());
            throw new ResourceConflictException("Existed Supplier Name with an id: " + supplier.getId());
        } else {
            Supplier supplier = new Supplier(
                    supplierRequestDTO.supplierName(), supplierRequestDTO.phoneNo(),
                    supplierRequestDTO.email(), supplierRequestDTO.address(),
                    supplierRequestDTO.street(), supplierRequestDTO.ward(),
                    supplierRequestDTO.city(), supplierRequestDTO.country(),
                    supplierRequestDTO.postalCode(), supplierRequestDTO.activeMode(),
                    new HashSet<>());
            supplier = supplierRepository.saveAndFlush(supplier);
            return new SupplierResponseDTO(supplier.getId(), supplier.getSupplierName(), supplier.getPhoneNo(),
                    supplier.getEmail(), supplier.getAddress(),
                    supplier.getStreet(), supplier.getWard(),
                    supplier.getCity(), supplier.getCountry(),
                    supplier.getPostalCode(), supplier.getActiveMode(),
                    new HashSet<>(supplier.getProducts().stream().map(Persistable::getId).toList()));
        }
    }

    @Override
    public List<SupplierResponseDTO> getSuppliers() {
        var suppliers = supplierRepository.findAll();
        List<SupplierResponseDTO> supplierResponseDTOs = new ArrayList<>();
        suppliers.forEach(supplier -> {
            Set<Long> productIds = new HashSet<>(supplier.getProducts().stream().map(Persistable::getId).toList());
            supplierResponseDTOs.add(new SupplierResponseDTO(
                    supplier.getId(), supplier.getSupplierName(), supplier.getPhoneNo(),
                    supplier.getEmail(), supplier.getAddress(), supplier.getStreet(),
                    supplier.getWard(), supplier.getCity(), supplier.getCountry(),
                    supplier.getPostalCode(), supplier.getActiveMode(), productIds));
        });
        return supplierResponseDTOs;
    }

    @Override
    public List<SupplierResponseDTO> getSuppliers(Long id) {
        List<SupplierResponseDTO> supplierResponseDTOs = new ArrayList<>();
        if (supplierRepository.existsById(id)) {
            Supplier supplier = supplierRepository.findById(id).get();
            Set<Long> productIds = new HashSet<>();
            supplier.getProducts().forEach(prod -> productIds.add(prod.getId()));
            supplierResponseDTOs.add(new SupplierResponseDTO(
                    supplier.getId(), supplier.getSupplierName(),
                    supplier.getPhoneNo(), supplier.getEmail(), supplier.getAddress(), supplier.getStreet(), supplier.getWard(),
                    supplier.getCity(), supplier.getCountry(), supplier.getPostalCode(), supplier.getActiveMode(), productIds));
            return supplierResponseDTOs;
        } else {
            throw new NotFoundException("Not found Supplier with an id: " + id);
        }
    }

    @Override
    @Transactional
    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierRequestDTO) {
        if (supplierRepository.existsById(id)) {
            Set<Product> products = new HashSet<>();
            supplierRequestDTO.products().forEach(prod -> {
                if (productRepository.existsById(prod)) {
                    products.add(productRepository.findById(prod).get());
                }
            });
            Supplier supplier = supplierRepository.findById(id).get();
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
            supplier.setProducts(products);
            supplier = supplierRepository.saveAndFlush(supplier);
            Set<Long> productIds = new HashSet<>();
            supplier.getProducts().forEach(prod -> productIds.add(prod.getId()));
            return new SupplierResponseDTO(
                    supplier.getId(), supplier.getSupplierName(),
                    supplier.getPhoneNo(), supplier.getEmail(), supplier.getAddress(), supplier.getStreet(), supplier.getWard(),
                    supplier.getCity(), supplier.getCountry(), supplier.getPostalCode(), supplier.getActiveMode(), productIds);
        } else {
            throw new NotFoundException("Not found Supplier with an id: " + id);
        }
    }
}
