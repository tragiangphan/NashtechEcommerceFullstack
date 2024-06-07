package com.nashtech.rookies.ecommerce.controllers.user;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignInRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignUpRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.AuthResponseTokenDTO;
import com.nashtech.rookies.ecommerce.handlers.utils.ErrorResponse;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.services.user.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/signUp", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<User> authSignUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        User newUser = userService.signUp(signUpRequestDTO);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> authSignIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO,
            HttpServletResponse httpResponse) throws AuthenticationException {
        try {
            // Authenticate this user
            String username = signInRequestDTO.email().split("@")[0] + "_"
                    + signInRequestDTO.email().split("@")[1].split("\\.")[0];
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    username,
                    signInRequestDTO.password());
            Authentication authUser = authenticationManager.authenticate(usernamePassword);
            User user = userService.loadUserByUsername(username);

            return ResponseEntity.ok(new AuthResponseTokenDTO(username, user.getRole().getId(),
                    userService.generateToken(authUser).get("access_token"),
                    userService.generateToken(authUser).get("refresh_token")));
        } catch (AuthenticationException e) {
            // Handle AuthenticationException with appropriate status code and message
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value()); // Set status code to 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials", e.getMessage()));
        }
    }

    @GetMapping()
    ResponseEntity<String> me(Authentication authentication) {
        if (authentication.getPrincipal() instanceof User user) {
            return ResponseEntity.ok(user.getUsername());
        }
        return ResponseEntity.badRequest().build();
    }
}
