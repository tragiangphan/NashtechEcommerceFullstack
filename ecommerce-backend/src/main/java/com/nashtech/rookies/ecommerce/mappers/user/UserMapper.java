package com.nashtech.rookies.ecommerce.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.models.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "roleId", source = "user.role.id")
  @Mapping(target = "inforId", source = "user.infor.id")
  UserRequestDTO toRequestDTO(User user);

  @Mapping(target = "roleId", source = "user.role.id")
  @Mapping(target = "inforId", source = "user.infor.id")
  UserResponseDTO toResponseDTO(User user);

  // @Mapping(target = "infor", ignore = true)
  // @Mapping(target = "role", ignore = true)
  User toRequestEntity(UserRequestDTO userDTO);

  // @Mapping(target = "infor", ignore = true)
  // @Mapping(target = "role", ignore = true)
  User toResponseEntity(UserResponseDTO userDTO);
}
