package com.nashtech.rookies.ecommerce.mappers.prod;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  CategoryRequestDTO toRequestDTO(Category category);

  @Mapping(target = "products", ignore = true)
  CategoryResponseDTO toResponseDTO(Category category);

  @Mapping(target = "products", ignore = true)
  Category toRequestEntity(CategoryRequestDTO categoryDTO);

  @Mapping(target = "products", ignore = true)
  Category toResponseEntity(CategoryResponseDTO categoryDTO);
}