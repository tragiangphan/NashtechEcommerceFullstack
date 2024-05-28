package com.nashtech.rookies.ecommerce.services.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Category;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.http.ResponseEntity;

public interface CategoryService extends CommonService<Category, Long> {
  CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

  ResponseEntity<?> handleGetCategory(CategoryGetRequestParamsDTO requestParamsDTO);

  CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);
}
