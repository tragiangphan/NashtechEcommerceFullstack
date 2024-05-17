package com.nashtech.rookies.ecommerce.mappers.prod;

import org.mapstruct.Mapper;
import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  CategoryRequestDTO toRequestDTO(Category category);
  CategoryResponseDTO toResponseDTO(Category category);

  Category toRequestEntity(CategoryRequestDTO categoryDTO);
  Category toResponseEntity(CategoryResponseDTO categoryDTO);
}