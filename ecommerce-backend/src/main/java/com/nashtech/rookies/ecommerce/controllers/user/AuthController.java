package com.nashtech.rookies.ecommerce.controllers.user;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignInRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignUpRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.AuthResponseTokenDTO;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.security.TokenProvider;
import com.nashtech.rookies.ecommerce.services.user.UserService;
import com.nashtech.rookies.ecommerce.services.user.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping(
            path = "/signUp",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> authSignUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        User newUser = userService.signUp(signUpRequestDTO);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping(
            path = "/signIn",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AuthResponseTokenDTO> authSignIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO) {
        try {
            User user = userService.signIn(signInRequestDTO);
            // Authenticate this user
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(), user.getAuthorities());
            Authentication authUser = authenticationManager.authenticate(usernamePassword);

            return ResponseEntity.ok(new AuthResponseTokenDTO(userService.generateToken(authUser).get("access_token"),
                    userService.generateToken(authUser).get("refresh_token")));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad credentials", e);
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
