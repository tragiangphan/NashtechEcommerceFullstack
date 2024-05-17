package com.nashtech.rookies.ecommerce.mappers.prod;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {
  ImageRequestDTO toRequestDTO(Image image);

  @Mapping(target = "productId", source = "image.product.id")
  ImageResponseDTO toResponseDTO(Image image);

  Image toRequestEntity(ImageRequestDTO dto);

  Image toResponseEntity(ImageResponseDTO dto);
}
