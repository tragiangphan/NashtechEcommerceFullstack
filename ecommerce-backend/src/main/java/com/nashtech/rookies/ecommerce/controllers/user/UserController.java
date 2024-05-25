package com.nashtech.rookies.ecommerce.controllers.user;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.user.responses.UserPaginationDTO;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.services.user.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestVersionConfig.API_VERSION + "/users")
public class UserController {
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping()
  @RolesAllowed("ROLE_ADMIN")
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
    log.info("Create user request: {}", userRequestDTO);
    return ResponseEntity.ok(userService.createUser(userRequestDTO));
  }

  @GetMapping()
  public ResponseEntity<UserPaginationDTO> getUsers(
      @Valid @RequestParam(name = "id", required = false) Long id,
      @RequestParam(name = "direction") Sort.Direction dir,
      @RequestParam(name = "pageNum") Integer pageNum,
      @RequestParam(name = "pageSize") Integer pageSize) {
    UserPaginationDTO userResponseDTO;
    if (id != null) {
      userResponseDTO = userService.getUsers(id);
    } else {
      userResponseDTO = userService.getUsers(dir, pageNum - 1, pageSize);
    }
    return ResponseEntity.ok(userResponseDTO);
  }

  @PutMapping()
  public ResponseEntity<UserResponseDTO> updateUserById(@RequestParam(name = "id", required = true) Long id,
      @RequestBody UserRequestDTO userRequestDTO) {
    return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
  }
}
