package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.models.prods.Supplier;
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
          throw new ResourceNotFoundException("Not found Supplier with an id: " + supplier);
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
      throw new ResourceNotFoundException("Not found Category with an id: " + productRequestDTO.categoryId());
    }
  }

  @Override
  public List<ProductResponseDTO> getProducts() {
    var products = productRepository.findAll();
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
    return productResponseDTOs;
  }

  @Override
  public List<ProductResponseDTO> getProducts(Long id) {
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
      return productResponseDTOs;
    } else {
      throw new ResourceNotFoundException(productNotFoundMessage + id);
    }
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
        throw new ResourceNotFoundException("Not found Category with an id: " + productRequestDTO.categoryId());
      }
      Set<Supplier> suppliers = new HashSet<>();
      productRequestDTO.suppliers().forEach(supplier -> {
        if (supplierRepository.existsById(supplier)) {
          suppliers.add(supplierRepository.findById(supplier).get());
        } else {
          throw new ResourceNotFoundException("Not found Supplier with an id: " + supplier);
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
      throw new ResourceNotFoundException(productNotFoundMessage + id);
    }
  }
}
