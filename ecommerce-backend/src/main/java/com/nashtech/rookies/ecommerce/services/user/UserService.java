package com.nashtech.rookies.ecommerce.services.user;

import java.util.List;
import java.util.Optional;

import com.nashtech.rookies.ecommerce.dto.user.requests.SignInRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignUpRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.UserRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserPaginationDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.UserResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.exceptions.UserExistException;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends CommonService<User, Long> {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserPaginationDTO getUsers(Sort.Direction dir, int pageNum, int pageSize);

    UserPaginationDTO getUsers(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    boolean existsUserByEmail(String email);

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    User signUp(SignUpRequestDTO signUpRequestDTO) throws UserExistException;

    User signIn(SignInRequestDTO signInRequestDTO) throws ResourceNotFoundException;
}
