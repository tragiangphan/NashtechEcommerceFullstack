package com.nashtech.rookies.ecommerce.configs;

import com.nashtech.rookies.ecommerce.security.JwtAuthenticationFilter;
import com.nashtech.rookies.ecommerce.security.TokenProvider;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthConfig {
    @Bean
    JwtAuthenticationFilter authFilter(TokenProvider tokenProvider, UserDetailsService userService) {
        return new JwtAuthenticationFilter(tokenProvider, userService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                            JwtAuthenticationFilter authFilter) throws Exception {
        String categoriesAPI = "/api/categories";
        String suppliersAPI = "/api/suppliers";
        String productsAPI = "api/v1/products";
        String imagesAPI = "api/v1/images";
        String cartAPI = "api/v1/cart";
        String cartItemsAPI = "api/v1/cartItems";
        String ordersAPI = "api/v1/orders";
        String ratingsAPI = "api/v1/ratings";
        String usersAPI = "api/v1/users";
        String inforsAPI = "api/v1/infors";
        String signUpAPI = "api/v1/auth/signUp";
        String signInAPI = "api/v1/auth/signIn";

        return httpSecurity
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOriginPatterns(List.of("http://localhost:[*]"));
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    configuration.setAllowedHeaders(List.of("Authorization", "Content-type", "Access-Control-Allow-Credentials", "Access-Control-Allow-Headers"));
                    configuration.setAllowCredentials(Boolean.TRUE);
                    return configuration;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/api-docs/**"
                        ).permitAll()
                        // .requestMatchers(HttpMethod.POST,
                        //         signUpAPI,      signInAPI
                        // ).permitAll()
                        // .requestMatchers(HttpMethod.POST,
                        //         categoriesAPI,      suppliersAPI,       productsAPI,        imagesAPI,
                        //         cartAPI,
                        //         usersAPI
                        // ).hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.POST,
                        //         cartAPI,            cartItemsAPI,       ordersAPI,
                        //         ratingsAPI,
                        //         inforsAPI
                        // ).hasRole("CUSTOMER")
                        // .requestMatchers(HttpMethod.GET,
                        //         categoriesAPI,      suppliersAPI,       productsAPI,        imagesAPI,
                        //         ratingsAPI
                        // ).permitAll()
                        // .requestMatchers(HttpMethod.GET,
                        //         usersAPI,           inforsAPI
                        // ).hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.GET,
                        //         categoriesAPI,      suppliersAPI,       productsAPI,        imagesAPI,
                        //         cartAPI,            cartItemsAPI,       ordersAPI,          ratingsAPI,
                        //         usersAPI,           inforsAPI,          signUpAPI,          signInAPI
                        // ).hasRole("CUSTOMER")
                        // .requestMatchers(HttpMethod.PUT,
                        //         categoriesAPI,      suppliersAPI,       productsAPI,        imagesAPI,
                        //         cartAPI,            cartItemsAPI,       ordersAPI,          ratingsAPI,
                        //         usersAPI,           inforsAPI,          signUpAPI,          signInAPI
                        // ).permitAll()
                        // .requestMatchers(HttpMethod.PUT,
                        //         categoriesAPI,      suppliersAPI,       productsAPI,        imagesAPI,
                        //         cartAPI,            cartItemsAPI,       ordersAPI,          ratingsAPI,
                        //         usersAPI,           inforsAPI,          signUpAPI,          signInAPI
                        // ).hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.PUT,
                        //         categoriesAPI,      suppliersAPI,       productsAPI,        imagesAPI,
                        //         cartAPI,            cartItemsAPI,       ordersAPI,          ratingsAPI,
                        //         usersAPI,           inforsAPI,          signUpAPI,          signInAPI
                        // ).hasRole("CUSTOMER")
                        // .requestMatchers(HttpMethod.DELETE,
                        //         cartItemsAPI
                        // ).hasRole("CUSTOMER")
                        .requestMatchers(
                                categoriesAPI,      suppliersAPI,       productsAPI,        imagesAPI,
                                cartAPI,            cartItemsAPI,       ordersAPI,          ratingsAPI,
                                usersAPI,           inforsAPI,          signUpAPI,          signInAPI
                        ).permitAll()
                        .anyRequest().permitAll())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManagerJwt(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
