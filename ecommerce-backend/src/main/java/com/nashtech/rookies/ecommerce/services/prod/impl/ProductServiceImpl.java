package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductPaginationDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.constants.FeatureModeEnum;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
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

    @Override
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
                        product.getCreatedOn(), product.getLastUpdatedOn(),
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
    public ResponseEntity<?> handleGetProduct(ProductGetRequestParamsDTO requestParamsDTO) {
        ProductPaginationDTO productPaginationDTO;
        ProductResponseDTO productResponseDTO;

        if (requestParamsDTO.id() != null) {
            productResponseDTO = getProductById(requestParamsDTO.id());
            return ResponseEntity.ok(productResponseDTO);
        } else if (requestParamsDTO.productName() != null) {
            productPaginationDTO = getProductAllByProductName(requestParamsDTO.productName(), requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(productPaginationDTO);
        } else if (requestParamsDTO.categoryName() != null) {
            productPaginationDTO = getProductAllByCategoryName(requestParamsDTO.categoryName(), requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(productPaginationDTO);
        } else if (requestParamsDTO.featureMode() != null) {
            productPaginationDTO = getProductByFeatureMode(requestParamsDTO.featureMode(), requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(productPaginationDTO);
        } else if (requestParamsDTO.maxPrice() != null && requestParamsDTO.minPrice() != null) {
            productPaginationDTO = getProductByPriceRange(requestParamsDTO.maxPrice(), requestParamsDTO.minPrice(),
                    requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(productPaginationDTO);
        } else {
            productPaginationDTO = getProducts(requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(productPaginationDTO);
        }
    }

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
                    product.getCreatedOn(), product.getLastUpdatedOn(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    public ProductResponseDTO getProductById(Long id) {
        if (productRepository.existsById(id)) {
            Product product = productRepository.findById(id).get();
            Set<Long> images = new HashSet<>();
            product.getImages().forEach(image -> images.add(image.getId()));
            Set<Long> suppliers = new HashSet<>();
            product.getSuppliers().forEach(supplier -> suppliers.add(supplier.getId()));
            return new ProductResponseDTO(
                    product.getId(), product.getProductName(),
                    product.getProductDesc(), product.getUnit(),
                    product.getPrice(), product.getQuantity(),
                    product.getFeatureMode(), product.getCategory().getId(), 
                    product.getCreatedOn(), product.getLastUpdatedOn(),
                    suppliers, images);
        } else {
            throw new NotFoundException(productNotFoundMessage + id);
        }
    }

    public ProductPaginationDTO getProductAllByProductName(String productName, Sort.Direction dir,
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
                    product.getCreatedOn(), product.getLastUpdatedOn(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    public ProductPaginationDTO getProductAllByCategoryName(String categoryName, Sort.Direction dir, int pageNum,
            int pageSize) {
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
                    product.getCreatedOn(), product.getLastUpdatedOn(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    public ProductPaginationDTO getProductByPriceRange(Long maxPrice, Long minPrice, Sort.Direction dir, int pageNum,
            int pageSize) {
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
                    product.getCreatedOn(), product.getLastUpdatedOn(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    public ProductPaginationDTO getProductByFeatureMode(FeatureModeEnum featureMode, Sort.Direction dir, int pageNum,
            int pageSize) {
        Sort sort = Sort.by(dir, "product_name");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> products = productRepository.getProductByFeatureMode(featureMode, pageable);
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
                    product.getCreatedOn(), product.getLastUpdatedOn(),
                    suppliers, images));
        });
        return new ProductPaginationDTO(products.getTotalPages(), products.getTotalElements(), products.getSize(),
                products.getNumber() + 1, productResponseDTOs);
    }

    @Override
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
                    product.getCreatedOn(), product.getLastUpdatedOn(),
                    supplierIds, images);
        } else {
            throw new NotFoundException(productNotFoundMessage + id);
        }
    }
}
