package com.nashtech.rookies.ecommerce.controllers.prod;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.CategoryMapper;
import com.nashtech.rookies.ecommerce.models.prod.Category;
import com.nashtech.rookies.ecommerce.services.prod.CategoryService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Slf4j
public class CategoryController extends RestVersion {
  private CategoryService categoryService;
  private CategoryMapper categoryMapper;

  public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
    this.categoryService = categoryService;
    this.categoryMapper = categoryMapper;
  }

  @PostMapping("/categories")
  public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO categoryDTO) {
    Category category = categoryService.save(categoryMapper.toRequestEntity(categoryDTO));
    return ResponseEntity.ok(categoryMapper.toResponseDTO(category));
  }

  @GetMapping("/categories")
  public ResponseEntity<List<CategoryResponseDTO>> getCategoryMethod(
      @RequestParam(name = "id", required = false) Long id,
      @RequestParam(name = "categoryName", required = false) String categoryName) {
    var categoryResponseDTO = new LinkedList<CategoryResponseDTO>();

    if (id != null) {
      Category category = categoryService.findOne(id).orElseThrow(ResourceNotFoundException::new);
      CategoryResponseDTO categoryDTO = categoryMapper.toResponseDTO(category);
      categoryResponseDTO.add(categoryDTO);
    } else if (categoryName != null) {
      log.info("Filter by Category Name");
    } else {
      var categories = categoryService.findAll();
      for (var category : categories) {
        CategoryResponseDTO categoryDTO = categoryMapper.toResponseDTO(category);
        categoryResponseDTO.add(categoryDTO);
      }
    }
    return ResponseEntity.ok(categoryResponseDTO);
  }

  @PutMapping("/categories/")
  public ResponseEntity<CategoryResponseDTO> updateCategoryById(@RequestParam(name = "id", required = true) Long id,
      @RequestBody CategoryRequestDTO categoryRequestDTO) {
    Category category = categoryService.findOne(id).orElseThrow(ResourceNotFoundException::new);
    category.setCategoryName(categoryRequestDTO.categoryName());
    category.setCategoryDesc(categoryRequestDTO.categoryDesc());
    category.setActiveMode(categoryRequestDTO.activeMode());
    category = categoryService.save(category);
    return ResponseEntity.ok(categoryMapper.toResponseDTO(category));
  }
}
