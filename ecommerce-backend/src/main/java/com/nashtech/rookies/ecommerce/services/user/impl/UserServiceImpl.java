package com.nashtech.rookies.ecommerce.services.user.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.nashtech.rookies.ecommerce.dto.user.requests.SignInRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignUpRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserPaginationDTO;
import com.nashtech.rookies.ecommerce.exceptions.UserExistException;
import com.nashtech.rookies.ecommerce.models.cart.Cart;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.models.user.Infor;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserServiceImpl extends CommonServiceImpl<User, Long> implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private String userNotFoundMessage = "Not found User with an id: ";
    private String roleNotFoundMessage = "Not found Role with an id: ";

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UserPaginationDTO getUsers(Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<User> users = userRepository.findAll(pageable);
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
        return new UserPaginationDTO(users.getTotalPages(), users.getTotalElements(), users.getSize(),
                users.getNumber() + 1, userResponseDTOs);
    }

    @Override
    public UserPaginationDTO getUsers(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<User> users = userRepository.findAll(pageable);
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
                return new UserPaginationDTO(users.getTotalPages(), users.getTotalElements(), users.getSize(),
                        users.getNumber() + 1, userResponseDTOs);
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

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public UserDetails signUp(SignUpRequestDTO signUpRequestDTO) throws UserExistException {
        var user = userRepository.findOneByEmail(signUpRequestDTO.email());
        if (user.isPresent()) {
            throw new UserExistException("Already exists an user with email: " + signUpRequestDTO.email());
        } else {
            User newUser = new User();
            String encryptedPassword = passwordEncoder.encode(signUpRequestDTO.password());
            newUser.setInfor(new Infor(newUser));
            newUser.setCart(new Cart(newUser));
            newUser.setPassword(encryptedPassword);
            newUser.setEmail(signUpRequestDTO.email());
            if (!signUpRequestDTO.role().isEmpty()) {
                var userRole = roleRepository.findByRoleName(signUpRequestDTO.role());
                if (userRole == null) {
                    throw new UserExistException("Not found any role with name: " + signUpRequestDTO.role());
                }
                newUser.setRole(userRole);
            }
            return userRepository.saveAndFlush(newUser);
        }
    }

    @Override
    public UserDetails signIn(SignInRequestDTO signInRequestDTO) throws ResourceNotFoundException {
        var user = userRepository.findOneByEmail(signInRequestDTO.email());
        if (user.isEmpty()) {
            throw new UserExistException("Not exists an user with email: " + signInRequestDTO.email());
        } else {
            return user.get();
        }
    }
}
