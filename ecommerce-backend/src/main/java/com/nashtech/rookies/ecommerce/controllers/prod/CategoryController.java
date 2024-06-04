package com.nashtech.rookies.ecommerce.controllers.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryGetRequestParamsDTO;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.CategoryService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getCategoryMethod(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "direction", required = false) Sort.Direction dir,
            @RequestParam(name = "pageNum", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return categoryService.handleGetCategory(new CategoryGetRequestParamsDTO(id, categoryName, dir, pageNum, pageSize));
    }

    @PutMapping()
    public ResponseEntity<CategoryResponseDTO> updateCategoryById(@RequestParam(name = "id", required = true) Long id,
            @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequestDTO));
    }
}
