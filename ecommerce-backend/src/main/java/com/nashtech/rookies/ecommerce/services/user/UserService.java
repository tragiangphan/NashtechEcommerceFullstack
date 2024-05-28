package com.nashtech.rookies.ecommerce.services.user;

import com.nashtech.rookies.ecommerce.dto.user.requests.SignUpRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.UserGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

public interface UserService extends CommonService<User, Long> {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    ResponseEntity<?> handleGetUser(UserGetRequestParamsDTO requestParamsDTO);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    User signUp(SignUpRequestDTO signUpRequestDTO) throws ResourceConflictException;

    Map<String, String> generateToken(Authentication authenticationManager);
}
