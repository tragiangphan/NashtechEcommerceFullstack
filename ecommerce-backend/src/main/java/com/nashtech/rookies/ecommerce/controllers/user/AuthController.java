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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
            log.info("step 1: New User  : {} - {}", user.getUsername(), user.getPassword());

            // Authenticate this user
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
            log.info("step 2: Authen    : {} - {}", usernamePassword.getPrincipal(), usernamePassword.getCredentials());
            Authentication authUser = authenticationManager.authenticate(usernamePassword);

            log.info("step 3: Generating Token");
            // Generated token
            var accessToken = tokenProvider.generateToken((User) authUser.getPrincipal(), 2);
            var refreshToken = tokenProvider.generateToken((User) authUser.getPrincipal(), 24);

            log.info("Generated Token: access - {}, refresh - {}", accessToken, refreshToken);
            return ResponseEntity.ok(new AuthResponseTokenDTO(accessToken, refreshToken));
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
