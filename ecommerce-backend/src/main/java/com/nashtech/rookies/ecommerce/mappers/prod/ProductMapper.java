package com.nashtech.rookies.ecommerce.mappers.prod;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.models.prods.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(target = "images", ignore = true)
  @Mapping(target = "suppliers", ignore = true)
  @Mapping(target = "categoryId", source = "product.category.id")
  ProductResponseDTO toResponseDTO(Product product);

  @Mapping(target = "suppliers", ignore = true)
  @Mapping(target = "categoryId", source = "product.category.id")
  ProductRequestDTO toRequestDTO(Product product);

  @Mapping(target = "images", ignore = true)
  @Mapping(target = "suppliers", ignore = true)
  @Mapping(target = "cartItem", ignore = true)
  @Mapping(target = "category", ignore = true)
  Product toResponseEntity(ProductResponseDTO productDTO);

  @Mapping(target = "images", ignore = true)
  @Mapping(target = "suppliers", ignore = true)
  @Mapping(target = "cartItem", ignore = true)
  @Mapping(target = "category", ignore = true)
  Product toRequestEntity(ProductRequestDTO productDTO);

}