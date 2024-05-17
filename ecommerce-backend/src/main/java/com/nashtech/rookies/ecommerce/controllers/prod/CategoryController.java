package com.nashtech.rookies.ecommerce.controllers.prod;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.mappers.prod.CategoryMapper;
import com.nashtech.rookies.ecommerce.models.prod.Category;
import com.nashtech.rookies.ecommerce.services.prod.CategoryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class CategoryController extends RestVersion {
  private CategoryService categoryService;
  private CategoryMapper categoryMapper;

  public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
    this.categoryService = categoryService;
    this.categoryMapper = categoryMapper;
  }

  @PostMapping("/categories")
  public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryRequestDTO categoryDTO) {
    Category category = categoryService.save(categoryMapper.toRequestEntity(categoryDTO));
    return ResponseEntity.created(URI.create("/api/v1/categories/" + category.getId())).build();
  }

  @GetMapping("/categories")
  public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
    var categories = categoryService.findAll();
    var categoryResponseDTO = new LinkedList<CategoryResponseDTO>();
    for (var category : categories) {
      CategoryResponseDTO categoryDTO = categoryMapper.toResponseDTO(category);
      categoryResponseDTO.add(categoryDTO);
    }
    return ResponseEntity.ok(categoryResponseDTO);
  }

  @GetMapping("/categories/{id}")
  public ResponseEntity<CategoryResponseDTO> getCategoryById(@RequestParam("id") @PathVariable("id") Long id) {
    Category category = categoryService.findOne(id).orElseThrow(IllegalArgumentException::new);
    CategoryResponseDTO categoryDTO = categoryMapper.toResponseDTO(category);
    return ResponseEntity.ok(categoryDTO);
  }

  @PutMapping("/categories/{id}")
  public ResponseEntity<CategoryResponseDTO> updateCategoryById(@RequestParam("id") @PathVariable Long id,
      @RequestBody CategoryRequestDTO categoryRequestDTO) {
    Category category = categoryService.findOne(id).orElseThrow(IllegalArgumentException::new);
    category.setCategoryName(categoryRequestDTO.categoryName());
    category.setCategoryDesc(categoryRequestDTO.categoryDesc());
    category.setActive(categoryRequestDTO.isActive());
    category = categoryService.save(category);
    return ResponseEntity.ok(categoryMapper.toResponseDTO(category));
  }

}
