package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.exceptions.NotFoundException;
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

  private final ProductRepository prodRepository;
  private final CategoryRepository categoryRepository;
  private final SupplierRepository supplierRepository;

  ProductServiceImpl(ProductRepository prodRepository, CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
    super(prodRepository);
    this.prodRepository = prodRepository;
    this.categoryRepository = categoryRepository;
    this.supplierRepository = supplierRepository;
  }

  @Transactional
  public Product createProductManual(ProductRequestDTO productRequestDTO) {
    Product product = new Product();
    Category category = categoryRepository.findById(productRequestDTO.categoryId()).orElseThrow(NotFoundException::new);
    Set<Supplier> suppliers = new HashSet<>();
    for (Long supplierID : productRequestDTO.suppliers()) {
      suppliers.add(supplierRepository.findById(supplierID).orElseThrow(NotFoundException::new));
    }
    product.setProductName(productRequestDTO.productName());
    product.setProductDesc(productRequestDTO.productDesc());
    product.setUnit(productRequestDTO.unit());
    product.setPrice(productRequestDTO.price());
    product.setQuantity(productRequestDTO.quantity());
    product.setFeatured(productRequestDTO.isFeatured());
    product.setSuppliers(suppliers);
    product.setCategory(category);
    prodRepository.saveAndFlush(product);
    return product;
  }

  @Transactional
  public Product updateProductManual(Product product, ProductRequestDTO productRequestDTO) {
    Category category = categoryRepository.findById(productRequestDTO.categoryId()).orElseThrow(NotFoundException::new);
    Set<Supplier> suppliers = new HashSet<>();
    for (Long supplierID : productRequestDTO.suppliers()) {
      suppliers.add(supplierRepository.findById(supplierID).orElseThrow(NotFoundException::new));
    }
    product.setProductName(productRequestDTO.productName());
    product.setProductDesc(productRequestDTO.productDesc());
    product.setUnit(productRequestDTO.unit());
    product.setPrice(productRequestDTO.price());
    product.setQuantity(productRequestDTO.quantity());
    product.setFeatured(productRequestDTO.isFeatured());
    product.setSuppliers(suppliers);
    product.setCategory(category);
    prodRepository.saveAndFlush(product);
    return product;
  }
}
