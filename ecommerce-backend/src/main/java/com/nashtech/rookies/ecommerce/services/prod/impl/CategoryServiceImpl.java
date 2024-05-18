package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.CategoryMapper;
import com.nashtech.rookies.ecommerce.models.prod.Category;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.CategoryService;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl extends CommonServiceImpl<Category, Long> implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
    super(categoryRepository);
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
  }

  @Override
  @Transactional
  public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
    Category category = categoryRepository.saveAndFlush(categoryMapper.toRequestEntity(categoryRequestDTO));
    return categoryMapper.toResponseDTO(category);
  }

  @Override
  public List<CategoryResponseDTO> getCategories() {
    var categories = categoryRepository.findAll();
    List<CategoryResponseDTO> categoryResponseDTOs = new ArrayList<>();
    categories.forEach(category -> categoryResponseDTOs.add(categoryMapper.toResponseDTO(category)));
    return categoryResponseDTOs;
  }

  @Override
  public List<CategoryResponseDTO> getCategories(Long id) {
    var category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    List<CategoryResponseDTO> categoryResponseDTOs = new ArrayList<>();
    categoryResponseDTOs.add(categoryMapper.toResponseDTO(category));
    return categoryResponseDTOs;
  }

  @Override
  @Transactional
  public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
    Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    category.setCategoryName(categoryRequestDTO.categoryName());
    category.setCategoryDesc(categoryRequestDTO.categoryDesc());
    category.setActiveMode(categoryRequestDTO.activeMode());
    category = categoryRepository.saveAndFlush(category);
    return categoryMapper.toResponseDTO(category);
  }
}
