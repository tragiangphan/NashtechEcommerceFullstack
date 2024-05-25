package com.nashtech.rookies.ecommerce.controllers.user;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignInRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.SignUpRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.AuthResponseTokenDTO;
import com.nashtech.rookies.ecommerce.models.user.User;
import com.nashtech.rookies.ecommerce.security.TokenProvider;
import com.nashtech.rookies.ecommerce.services.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }


    @PostMapping(
            path = "/signUp",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDetails> authSignUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        // Created user
        UserDetails newUser = userService.signUp(signUpRequestDTO);

        return ResponseEntity.ok(newUser);
    }

    @PostMapping(
            path = "/signIn",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AuthResponseTokenDTO> authSignIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO) {
        UserDetails user = userService.signIn(signInRequestDTO);
        log.info("step 1: New user: {}, {}", user.getUsername(), user.getPassword());

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword());
        log.info("step 2: Authen: {}, {}", usernamePassword.getPrincipal(), usernamePassword.getCredentials());

        // Authenticate this user
        Authentication authUser = authenticationManager.authenticate(usernamePassword);

        log.info("step 3: Authenticated user");

        // Generated token
        var accessToken = tokenProvider.generateToken((User) authUser.getPrincipal(), 2);
        var refreshToken = tokenProvider.generateToken((User) authUser.getPrincipal(), 24);

        log.warn("Access Token user: {}", accessToken);
        log.warn("Access Refresh user: {}", refreshToken);
        return ResponseEntity.ok(new AuthResponseTokenDTO(accessToken, refreshToken));

    }


    @GetMapping()
    ResponseEntity<String> me(Authentication authentication) {
        if (authentication.getPrincipal() instanceof User user) {
            return ResponseEntity.ok(user.getUsername());
        }
        return ResponseEntity.badRequest().build();
    }
}
