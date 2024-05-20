package com.nashtech.rookies.ecommerce.services.user;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface UserService extends CommonService<User, Long> {
  UserResponseDTO createUser(UserRequestDTO userRequestDTO);

  List<UserResponseDTO> getUsers();

  List<UserResponseDTO> getUsers(Long id);

  UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
}
