package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductPaginationDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.ProductService;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl extends CommonServiceImpl<Product, Long> implements ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    private String productNotFoundMessage = "Not found Product with an id: ";

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        if (!productRepository.existsByProductName(productRequestDTO.productName())) {
            Product product = new Product(
                    productRequestDTO.productName(), productRequestDTO.productDesc(),
                    productRequestDTO.unit(), productRequestDTO.price(),
                    productRequestDTO.quantity(), productRequestDTO.featureMode());
            if (categoryRepository.existsById(productRequestDTO.categoryId())) {
                product.setCategory(categoryRepository.findById(productRequestDTO.categoryId()).get());
                Set<Supplier> suppliers = new HashSet<>();
                productRequestDTO.suppliers().forEach(supplier -> {
                    if (supplierRepository.existsById(supplier)) {
                        suppliers.add(supplierRepository.findById(supplier).get());
                    } else {
                        throw new NotFoundException("Not found Supplier with an id: " + supplier);
                    }
                });
                product.setSuppliers(suppliers);
                product = productRepository.saveAndFlush(product);
                Set<Long> supplierIds = new HashSet<>(product.getSuppliers().stream().map(Persistable::getId).toList());
                return new ProductResponseDTO(
                        product.getId(), product.getProductName(),
                        product.getProductDesc(), product.getUnit(),
                        product.getPrice(), product.getQuantity(),
                        product.getFeatureMode(), product.getCategory().getId(),
                        supplierIds, new HashSet<>());
            } else {
                throw new NotFoundException("Not found Category with an id: " + productRequestDTO.categoryId());
            }
        } else {
            Product product = productRepository.findByProductName(productRequestDTO.productName());
            throw new ResourceConflictException("Existed Product Name with an id: " + product.getId());
        }
    }

    @Override
    public ProductPaginationDTO getProducts(Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        products.forEach(product -> {
            Set<Long> images = new HashSet<>();
            product.getImages().forEach(image -> images.add(image.getId()));
            Set<Long> suppliers = new HashSet<>();
            product.getSuppliers().forEach(supplier -> suppliers.add(supplier.getId()));
            productResponseDTOs.add(new ProductResponseDTO(
                    product.getId(), product.getProductName(),
                    product.getProductDesc(), product.getUnit(),
                    product.getPrice(), product.getQuantity(),
                    product.getFeatureMode(), product.getCategory().getId(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    @Override
    public ProductPaginationDTO getProducts(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        if (productRepository.existsById(id)) {
            Product product = productRepository.findById(id).get();
            Set<Long> images = new HashSet<>();
            product.getImages().forEach(image -> images.add(image.getId()));
            Set<Long> suppliers = new HashSet<>();
            product.getSuppliers().forEach(supplier -> suppliers.add(supplier.getId()));
            productResponseDTOs.add(new ProductResponseDTO(
                    product.getId(), product.getProductName(),
                    product.getProductDesc(), product.getUnit(),
                    product.getPrice(), product.getQuantity(),
                    product.getFeatureMode(), product.getCategory().getId(),
                    suppliers, images));
            return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                    products.getNumber() + 1, productResponseDTOs);
        } else {
            throw new NotFoundException(productNotFoundMessage + id);
        }
    }

    @Override
    public ProductPaginationDTO getProductByProductName(String productName, Sort.Direction dir,
                                                        int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "product_name");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> products = productRepository.findAllByProductNameLike(
                "%" + productName + "%", pageable);
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        products.forEach(product -> {
            Set<Long> images = new HashSet<>();
            product.getImages().forEach(image -> images.add(image.getId()));
            Set<Long> suppliers = new HashSet<>();
            product.getSuppliers().forEach(supplier -> suppliers.add(supplier.getId()));
            productResponseDTOs.add(new ProductResponseDTO(
                    product.getId(), product.getProductName(),
                    product.getProductDesc(), product.getUnit(),
                    product.getPrice(), product.getQuantity(),
                    product.getFeatureMode(), product.getCategory().getId(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    @Override
    public ProductPaginationDTO getProductByCategoryName(String categoryName, Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "product_name");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> products = productRepository.findAllByCategoryName(
                "%" + categoryName + "%", pageable);
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        products.forEach(product -> {
            Set<Long> images = new HashSet<>();
            product.getImages().forEach(image -> images.add(image.getId()));
            Set<Long> suppliers = new HashSet<>();
            product.getSuppliers().forEach(supplier -> suppliers.add(supplier.getId()));
            productResponseDTOs.add(new ProductResponseDTO(
                    product.getId(), product.getProductName(),
                    product.getProductDesc(), product.getUnit(),
                    product.getPrice(), product.getQuantity(),
                    product.getFeatureMode(), product.getCategory().getId(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    @Override
    public ProductPaginationDTO getProductByPriceRange(Long maxPrice, Long minPrice,
                                                                             Sort.Direction dir,
                                                                             int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "product_name");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> products = productRepository.getProductByPriceRange(maxPrice,
                minPrice, pageable);
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        products.forEach(product -> {
            Set<Long> images = new HashSet<>();
            product.getImages().forEach(image -> images.add(image.getId()));
            Set<Long> suppliers = new HashSet<>();
            product.getSuppliers().forEach(supplier -> suppliers.add(supplier.getId()));
            productResponseDTOs.add(new ProductResponseDTO(
                    product.getId(), product.getProductName(),
                    product.getProductDesc(), product.getUnit(),
                    product.getPrice(), product.getQuantity(),
                    product.getFeatureMode(), product.getCategory().getId(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        if (productRepository.existsById(id)) {
            Product product = productRepository.findById(id).get();
            product.setProductName(productRequestDTO.productName());
            product.setProductDesc(productRequestDTO.productDesc());
            product.setUnit(productRequestDTO.unit());
            product.setPrice(productRequestDTO.price());
            product.setQuantity(productRequestDTO.quantity());
            product.setFeatureMode(productRequestDTO.featureMode());
            if (categoryRepository.existsById(productRequestDTO.categoryId())) {
                product.setCategory(categoryRepository.findById(productRequestDTO.categoryId()).get());
            } else {
                throw new NotFoundException("Not found Category with an id: " + productRequestDTO.categoryId());
            }
            Set<Supplier> suppliers = new HashSet<>();
            productRequestDTO.suppliers().forEach(supplier -> {
                if (supplierRepository.existsById(supplier)) {
                    suppliers.add(supplierRepository.findById(supplier).get());
                } else {
                    throw new NotFoundException("Not found Supplier with an id: " + supplier);
                }
            });
            product.setSuppliers(suppliers);
            Set<Long> supplierIds = new HashSet<>();
            product.getSuppliers().forEach(supplier -> supplierIds.add(supplier.getId()));
            Set<Long> images = new HashSet<>();
            product.getImages().forEach(image -> images.add(image.getId()));
            return new ProductResponseDTO(product.getId(), product.getProductName(),
                    product.getProductDesc(), product.getUnit(),
                    product.getPrice(), product.getQuantity(),
                    product.getFeatureMode(), product.getCategory().getId(),
                    supplierIds, images);
        } else {
            throw new NotFoundException(productNotFoundMessage + id);
        }
    }
}
