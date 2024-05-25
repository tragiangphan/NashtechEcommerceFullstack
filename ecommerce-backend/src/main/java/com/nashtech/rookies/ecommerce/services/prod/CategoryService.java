package com.nashtech.rookies.ecommerce.services.prod;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.models.prods.Category;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface CategoryService extends CommonService<Category, Long> {
  CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

  List<CategoryResponseDTO> getCategories();

  List<CategoryResponseDTO> getCategories(Long id);

  CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);
}
