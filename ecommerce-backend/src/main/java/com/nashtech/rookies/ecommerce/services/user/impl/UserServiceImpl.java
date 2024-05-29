package com.nashtech.rookies.ecommerce.services.user.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.nashtech.rookies.ecommerce.dto.user.requests.SignUpRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.UserGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserPaginationDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.cart.Cart;
import com.nashtech.rookies.ecommerce.models.user.Infor;
import com.nashtech.rookies.ecommerce.security.TokenProvider;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    private String userNotFoundMessage = "Not found User with an id: ";
    private String roleNotFoundMessage = "Not found Role with an id: ";

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        super(userRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsUserByEmail(userRequestDTO.email())) {
            throw new ResourceConflictException("User with email " + userRequestDTO.email() + " already exists");
        } else {
            User user = new User();
            if (roleRepository.existsById(userRequestDTO.roleId())) {
                Role role = roleRepository.findById(userRequestDTO.roleId()).get();
                String encryptedPassword = passwordEncoder.encode(userRequestDTO.password());
                user.setRole(role);
                user.setFirstName(userRequestDTO.firstName());
                user.setLastName(userRequestDTO.lastName());
                user.setEmail(userRequestDTO.email());
                user.setPassword(encryptedPassword);
                user.setPhoneNo(userRequestDTO.phoneNo());
                user.setActiveMode(userRequestDTO.activeMode());
                user.setInfor(new Infor(user));
                user.setCart(new Cart(user, 0L));
                user.setOrders(new HashSet<>());
                user.setRatings(new HashSet<>());
                user = userRepository.saveAndFlush(user);
                return new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhoneNo(), user.getActiveMode(), user.getRole().getId(), user.getInfor().getId(), user.getCart().getId(), user.getOrders().stream().map(Persistable::getId).collect(Collectors.toSet()), user.getRatings().stream().map(Persistable::getId).collect(Collectors.toSet()));
            } else {
                throw new NotFoundException(roleNotFoundMessage + userRequestDTO.roleId());
            }
        }
    }

    @Override
    public ResponseEntity<?> handleGetUser(UserGetRequestParamsDTO requestParamsDTO) {
        UserPaginationDTO userResponseDTO;
        if (requestParamsDTO.id() != null) {
            userResponseDTO = getUsers(requestParamsDTO.id());
        } else {
            userResponseDTO = getUsers(requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
        }
        return ResponseEntity.ok(userResponseDTO);
    }

    public UserPaginationDTO getUsers(Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        users.forEach(user -> {
            Set<Long> orders = user.getOrders() != null ? user.getOrders().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>();
            Set<Long> ratings = user.getRatings() != null ? user.getRatings().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>();
            userResponseDTOs.add(new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhoneNo(), user.getActiveMode(), user.getRole().getId(), user.getInfor().getId(), user.getCart().getId(), orders, ratings));
        });
        return new UserPaginationDTO(users.getTotalPages(), users.getTotalElements(), users.getSize(), users.getNumber() + 1, userResponseDTOs);
    }

    public UserPaginationDTO getUsers(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).get();
            assert user.getRole().getId() != null;
            if (roleRepository.existsById(user.getRole().getId())) {
                Set<Long> orders = user.getOrders() != null ? user.getOrders().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>();
                Set<Long> ratings = user.getRatings() != null ? user.getRatings().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>();
                userResponseDTOs.add(new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhoneNo(), user.getActiveMode(), user.getRole().getId(), user.getInfor().getId(), user.getCart().getId(), orders, ratings));
                return new UserPaginationDTO(users.getTotalPages(), users.getTotalElements(), users.getSize(), users.getNumber() + 1, userResponseDTOs);
            } else {
                throw new NotFoundException(roleNotFoundMessage + user.getRole().getId());
            }
        } else {
            throw new NotFoundException(userNotFoundMessage + id);
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
                return new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhoneNo(), user.getActiveMode(), user.getRole().getId(), user.getInfor().getId(), user.getCart().getId(), orders, ratings);
            } else {
                throw new NotFoundException(roleNotFoundMessage + userRequestDTO.roleId());
            }
        } else {
            throw new NotFoundException(userNotFoundMessage + id);
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.existsUserByUsername(username)) {
            return userRepository.findOneByUsername(username).get();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    @Transactional
    public User signUp(SignUpRequestDTO signUpRequestDTO) throws ResourceConflictException {
        var user = userRepository.findOneByEmail(signUpRequestDTO.email());
        if (user.isPresent()) {
            throw new ResourceConflictException("Already exists an User with email: " + signUpRequestDTO.email());
        } else {
            User newUser = new User();
            String encryptedPassword = passwordEncoder.encode(signUpRequestDTO.password());
            newUser.setInfor(new Infor(newUser));
            newUser.setCart(new Cart(newUser, 0L));
            newUser.setPassword(encryptedPassword);
            newUser.setEmail(signUpRequestDTO.email());
            if (!signUpRequestDTO.role().isEmpty()) {
                var userRole = roleRepository.findByRoleName(signUpRequestDTO.role());
                if (userRole == null) {
                    throw new ResourceConflictException("Not found any role with name: " + signUpRequestDTO.role());
                }
                newUser.setRole(userRole);
            }
            return userRepository.saveAndFlush(newUser);
        }
    }
 
    @Override
    public Map<String, String> generateToken(Authentication authenticationManager) {
        // Generated token
        var accessToken = tokenProvider.generateToken((User) authenticationManager.getPrincipal(), 2);
        var refreshToken = tokenProvider.generateToken((User) authenticationManager.getPrincipal(), 24);
        return Map.of("access_token", accessToken, "refresh_token", refreshToken);
    }

}
