package com.nashtech.rookies.ecommerce.controllers.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.services.user.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController extends RestVersion {
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
    log.info("Create user request: {}", userRequestDTO);
    return ResponseEntity.ok(userService.createUser(userRequestDTO));
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDTO>> getAllUsers(
      @Valid @RequestParam(name = "id", required = false) Long id) {
    List<UserResponseDTO> userResponseDTO;
    if (id != null) {
      userResponseDTO = userService.getUsers(id);
    } else {
      userResponseDTO = userService.getUsers();
    }
    return ResponseEntity.ok(userResponseDTO);
  }

  @PutMapping("/users")
  public ResponseEntity<UserResponseDTO> updateUserById(@RequestParam(name = "id", required = true) Long id,
      @RequestBody UserRequestDTO userRequestDTO) {
    return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
  }
}
