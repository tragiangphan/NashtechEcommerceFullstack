package com.nashtech.rookies.ecommerce.services.user.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import com.nashtech.rookies.ecommerce.models.cart.Cart;
import com.nashtech.rookies.ecommerce.models.user.Infor;
import org.springframework.data.domain.Persistable;
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

    private String userNotFoundMessage = "Not found User with an id: ";
    private String roleNotFoundMessage = "Not found Role with an id: ";

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        if (roleRepository.existsById(userRequestDTO.roleId())) {
            Role role = roleRepository.findById(userRequestDTO.roleId()).get();
            user.setRole(role);
            user.setFirstName(userRequestDTO.firstName());
            user.setLastName(userRequestDTO.lastName());
            user.setEmail(userRequestDTO.email());
            user.setPassword(userRequestDTO.password());
            user.setPhoneNo(userRequestDTO.phoneNo());
            user.setActiveMode(userRequestDTO.activeMode());
            user.setInfor(new Infor(user));
            user.setCart(new Cart(user));
            user.setOrders(new HashSet<>());
            user.setRatings(new HashSet<>());
            user = userRepository.saveAndFlush(user);
            return new UserResponseDTO(
                    user.getId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getPassword(), user.getPhoneNo(),
                    user.getActiveMode(), user.getRole().getId(),
                    user.getInfor().getId(), user.getCart().getId(),
                    user.getOrders().stream().map(Persistable::getId).collect(Collectors.toSet()),
                    user.getRatings().stream().map(Persistable::getId).collect(Collectors.toSet()));
        } else {
            throw new ResourceNotFoundException(roleNotFoundMessage + userRequestDTO.roleId());
        }
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        var users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        users.forEach(user -> {
            Set<Long> orders = user.getOrders() != null ?
                    user.getOrders().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>();
            Set<Long> ratings = user.getRatings() != null ?
                    user.getRatings().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>();
            userResponseDTOs.add(new UserResponseDTO(
                    user.getId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getPassword(), user.getPhoneNo(),
                    user.getActiveMode(), user.getRole().getId(),
                    user.getInfor().getId(), user.getCart().getId(),
                    orders, ratings));
        });
        return userResponseDTOs;
    }

    @Override
    public List<UserResponseDTO> getUsers(Long id) {
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).get();
            assert user.getRole().getId() != null;
            if (roleRepository.existsById(user.getRole().getId())) {
                Set<Long> orders = user.getOrders() != null ?
                        user.getOrders().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>();
                Set<Long> ratings = user.getRatings() != null ?
                        user.getRatings().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>();
                userResponseDTOs.add(new UserResponseDTO(
                        user.getId(), user.getFirstName(), user.getLastName(),
                        user.getEmail(), user.getPassword(), user.getPhoneNo(),
                        user.getActiveMode(), user.getRole().getId(),
                        user.getInfor().getId(), user.getCart().getId(),
                        orders, ratings));
                return userResponseDTOs;
            } else {
                throw new ResourceNotFoundException(roleNotFoundMessage + user.getRole().getId());
            }
        } else {
            throw new ResourceNotFoundException(userNotFoundMessage + id);
        }
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).get();
            if (roleRepository.existsById(userRequestDTO.roleId())) {
                user.setRole(roleRepository.findById(userRequestDTO.roleId()).get());
                user.setFirstName(userRequestDTO.firstName());
                user.setLastName(userRequestDTO.lastName());
                user.setEmail(userRequestDTO.email());
                user.setPassword(userRequestDTO.password());
                user.setPhoneNo(userRequestDTO.phoneNo());
                user.setActiveMode(userRequestDTO.activeMode());
                user = userRepository.saveAndFlush(user);
                Set<Long> orders = new HashSet<>(user.getOrders().stream().map(Persistable::getId).toList());
                Set<Long> ratings = new HashSet<>(user.getRatings().stream().map(Persistable::getId).toList());
                return new UserResponseDTO(
                        user.getId(), user.getFirstName(), user.getLastName(),
                        user.getEmail(), user.getPassword(), user.getPhoneNo(),
                        user.getActiveMode(), user.getRole().getId(),
                        user.getInfor().getId(), user.getCart().getId(),
                        orders, ratings);
            } else {
                throw new ResourceNotFoundException(roleNotFoundMessage + userRequestDTO.roleId());
            }
        } else {
            throw new ResourceNotFoundException(userNotFoundMessage + id);
        }
    }
}
