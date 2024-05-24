package com.nashtech.rookies.ecommerce.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.user.requests.InforRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.InforResponseDTO;
import com.nashtech.rookies.ecommerce.models.user.Infor;

@Mapper(componentModel = "spring")
public interface InforMapper {
  @Mapping(target = "userId", source = "infor.user.id")
  InforRequestDTO toRequestDTO(Infor infor);

  @Mapping(target = "userId", source = "infor.user.id")
  InforResponseDTO toResponseDTO(Infor infor);

  @Mapping(target = "user", ignore = true)
  Infor toRequestEntity(InforRequestDTO inforDTO);

  @Mapping(target = "user", ignore = true)
  Infor toResponseEntity(InforResponseDTO inforDTO);
}
