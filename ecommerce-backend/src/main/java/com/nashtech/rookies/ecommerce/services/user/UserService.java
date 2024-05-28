package com.nashtech.rookies.ecommerce.services.user;

import com.nashtech.rookies.ecommerce.dto.user.requests.SignInRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignUpRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserPaginationDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;
import java.util.Optional;

public interface UserService extends CommonService<User, Long> {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserPaginationDTO getUsers(Sort.Direction dir, int pageNum, int pageSize);

    UserPaginationDTO getUsers(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    boolean existsUserByEmail(String email);

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    User signUp(SignUpRequestDTO signUpRequestDTO) throws ResourceConflictException;

    User signIn(SignInRequestDTO signInRequestDTO) throws NotFoundException;

    Map<String, String> generateToken(Authentication authenticationManager);
}
