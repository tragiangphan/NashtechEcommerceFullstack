package com.nashtech.rookies.ecommerce.services.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.user.UserMapper;
import com.nashtech.rookies.ecommerce.models.user.Role;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.repositories.user.RoleRepository;
import com.nashtech.rookies.ecommerce.repositories.user.UserRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.user.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends CommonServiceImpl<User, Long> implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final RoleRepository roleRepository;

  public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
    super(userRepository);
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.roleRepository = roleRepository;
  }

  @Transactional
  public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
    User user = new User();
    Role role = roleRepository.findById(userRequestDTO.roleId())
        .orElseThrow(() -> new ResourceNotFoundException("Not found Role with an id: " + userRequestDTO.roleId()));
    user.setRole(role);
    user.setFirstName(userRequestDTO.firstName());
    user.setLastName(userRequestDTO.lastName());
    user.setEmail(userRequestDTO.email());
    user.setPassword(userRequestDTO.password());
    user.setPhoneNo(userRequestDTO.phoneNo());
    user.setActiveMode(userRequestDTO.activeMode());
    user = userRepository.saveAndFlush(user);
    return userMapper.toResponseDTO(user);
  }

  @Override
  public List<UserResponseDTO> getUsers() {
    var users = userRepository
        .findAll();
    List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
    users.forEach(user -> userResponseDTOs.add(userMapper.toResponseDTO(user)));
    return userResponseDTOs;
  }

  @Override
  public List<UserResponseDTO> getUsers(Long id) {
    var user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found User with an id: " + id));
    List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
    userResponseDTOs.add(userMapper.toResponseDTO(user));
    return userResponseDTOs;
  }

  @Transactional
  public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
    Role role = roleRepository.findById(userRequestDTO.roleId())
        .orElseThrow(() -> new ResourceNotFoundException("Not found Role with an id:" + userRequestDTO.roleId()));
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found User with an id:" + id));
    user.setFirstName(userRequestDTO.firstName());
    user.setLastName(userRequestDTO.lastName());
    user.setEmail(userRequestDTO.email());
    user.setPassword(userRequestDTO.password());
    user.setPhoneNo(userRequestDTO.phoneNo());
    user.setActiveMode(userRequestDTO.activeMode());
    user.setRole(role);
    user = userRepository.saveAndFlush(user);
    return userMapper.toResponseDTO(user);
  }
}
