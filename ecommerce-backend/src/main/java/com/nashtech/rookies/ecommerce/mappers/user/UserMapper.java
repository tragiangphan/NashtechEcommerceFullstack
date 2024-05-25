package com.nashtech.rookies.ecommerce.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.models.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "roleId", source = "user.role.id")
  UserRequestDTO toRequestDTO(User user);

  @Mapping(target = "roleId", source = "user.role.id")
  @Mapping(target = "orders", ignore = true)
  @Mapping(target = "ratings", ignore = true)
  UserResponseDTO toResponseDTO(User user);
}
