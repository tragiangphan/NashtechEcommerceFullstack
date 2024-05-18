package com.nashtech.rookies.ecommerce.controllers.prod;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
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

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping("/categories")
  public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO categoryDTO) {
    return ResponseEntity.ok(categoryService.createCategory(categoryDTO));

  }

  @GetMapping("/categories")
  public ResponseEntity<List<CategoryResponseDTO>> getCategoryMethod(
      @RequestParam(name = "id", required = false) Long id) {
    List<CategoryResponseDTO> categoryResponseDTO;

    if (id != null) {
      categoryResponseDTO = categoryService.getCategories(id);
    } else {
      categoryResponseDTO = categoryService.getCategories();
    }
    return ResponseEntity.ok(categoryResponseDTO);
  }

  @PutMapping("/categories")
  public ResponseEntity<CategoryResponseDTO> updateCategoryById(@RequestParam(name = "id", required = true) Long id,
      @RequestBody CategoryRequestDTO categoryRequestDTO) {
    return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequestDTO));

  }
}
