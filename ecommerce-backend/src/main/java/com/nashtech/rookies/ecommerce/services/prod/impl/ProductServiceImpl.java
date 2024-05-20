package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.ProductMapper;
import com.nashtech.rookies.ecommerce.models.prod.Category;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Supplier;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.ProductService;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl extends CommonServiceImpl<Product, Long> implements ProductService {
  private final ProductMapper productMapper;
  private final ProductRepository productRepository;

  private final CategoryRepository categoryRepository;
  private final SupplierRepository supplierRepository;

  public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository,
      CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
    super(productRepository);
    this.productMapper = productMapper;
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.supplierRepository = supplierRepository;
  }

  @Transactional
  public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
    Product product = new Product();
    Category category = categoryRepository.findById(productRequestDTO.categoryId())
        .orElseThrow(
            () -> new ResourceNotFoundException("Not found Category with an id: " + productRequestDTO.categoryId()));
    Set<Supplier> suppliers = new HashSet<>();
    for (Long supplierID : productRequestDTO.suppliers()) {
      suppliers.add(supplierRepository.findById(supplierID)
          .orElseThrow(() -> new ResourceNotFoundException("Not found Supplier with an id: " + supplierID)));
    }
    product.setProductName(productRequestDTO.productName());
    product.setProductDesc(productRequestDTO.productDesc());
    product.setUnit(productRequestDTO.unit());
    product.setPrice(productRequestDTO.price());
    product.setQuantity(productRequestDTO.quantity());
    product.setFeatureMode(productRequestDTO.featureMode());
    product.setSuppliers(suppliers);
    product.setCategory(category);
    product = productRepository.saveAndFlush(product);
    return productMapper.toResponseDTO(product);
  }

  @Override
  public List<ProductResponseDTO> getProducts() {
    var products = productRepository.findAll();
    List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
    products.forEach(product -> productResponseDTOs.add(productMapper.toResponseDTO(product)));
    return productResponseDTOs;
  }

  @Override
  public List<ProductResponseDTO> getProducts(Long id) {
    List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Product with an id: " + id));
    productResponseDTOs.add(productMapper.toResponseDTO(product));
    return productResponseDTOs;
  }

  @Transactional
  public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Product with an id: " + id));
    Category category = categoryRepository.findById(productRequestDTO.categoryId())
        .orElseThrow(
            () -> new ResourceNotFoundException("Not found Category with an id: " + productRequestDTO.categoryId()));
    Set<Supplier> suppliers = new HashSet<>();
    for (Long supplierID : productRequestDTO.suppliers()) {
      suppliers.add(supplierRepository.findById(supplierID)
          .orElseThrow(() -> new ResourceNotFoundException("Not found Product with an id: " + supplierID)));
    }
    product.setProductName(productRequestDTO.productName());
    product.setProductDesc(productRequestDTO.productDesc());
    product.setUnit(productRequestDTO.unit());
    product.setPrice(productRequestDTO.price());
    product.setQuantity(productRequestDTO.quantity());
    product.setFeatureMode(productRequestDTO.featureMode());
    product.setSuppliers(suppliers);
    product.setCategory(category);
    product = productRepository.saveAndFlush(product);
    return productMapper.toResponseDTO(product);
  }
}
