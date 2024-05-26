package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.models.prods.Category;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.CategoryService;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl extends CommonServiceImpl<Category, Long> implements CategoryService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  CategoryServiceImpl(CategoryRepository categoryRepository,
      ProductRepository productRepository) {
    super(categoryRepository);
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  @Transactional
  public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
    Category category = new Category();
    category.setCategoryName(categoryRequestDTO.categoryName());
    category.setCategoryDesc(categoryRequestDTO.categoryDesc());
    category.setActiveMode(categoryRequestDTO.activeMode());
    category.setProducts(new HashSet<>());
    category = categoryRepository.saveAndFlush(category);
    return new CategoryResponseDTO(category.getId(),
        category.getCategoryName(), category.getCategoryDesc(),
        category.getActiveMode(), new HashSet<>());
  }

  @Override
  public List<CategoryResponseDTO> getCategories() {
    var categories = categoryRepository.findAll();
    List<Product> products = productRepository.findAll();
    
    List<CategoryResponseDTO> categoryResponseDTOs = new ArrayList<>();
    categories.forEach(category -> {
      Set<Product> productResponse = new HashSet<>();
      products.forEach(product -> {
        if (product.getCategory().getId().equals(category.getId())) {
          productResponse.add(product);
        }
      });
      category.setProducts(productResponse);
      Set<Long> productIds = new HashSet<>();
      productResponse.forEach(product -> productIds.add(product.getId()));
      categoryResponseDTOs.add(new CategoryResponseDTO(
          category.getId(), category.getCategoryName(),
          category.getCategoryDesc(), category.getActiveMode(), productIds));
    });
    return categoryResponseDTOs;
  }

  @Override
  public List<CategoryResponseDTO> getCategories(Long id) {
    var category = categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Not found Category with an id: " + id));
    List<CategoryResponseDTO> categoryResponseDTOs = new ArrayList<>();
    Set<Long> productIds = new HashSet<>();
    category.getProducts().forEach(product -> productIds.add(product.getId()));
    categoryResponseDTOs.add(new CategoryResponseDTO(id,
        category.getCategoryName(), category.getCategoryDesc(),
        category.getActiveMode(), productIds));
    return categoryResponseDTOs;
  }

  @Override
  @Transactional
  public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Not found Category with an id: " + id));
    category.setCategoryName(categoryRequestDTO.categoryName());
    category.setCategoryDesc(categoryRequestDTO.categoryDesc());
    category.setActiveMode(categoryRequestDTO.activeMode());
    category = categoryRepository.saveAndFlush(category);
    Set<Long> productIds = new HashSet<>();
    category.getProducts().forEach(prod -> productIds.add(prod.getId()));
    return new CategoryResponseDTO(category.getId(),
        category.getCategoryName(), category.getCategoryDesc(),
        category.getActiveMode(), productIds);
  }
}
