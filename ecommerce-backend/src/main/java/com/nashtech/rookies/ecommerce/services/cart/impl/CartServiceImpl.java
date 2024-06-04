package com.nashtech.rookies.ecommerce.services.cart.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.models.cart.CartItem;
import org.springframework.data.domain.Persistable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.mappers.cart.CartMapper;
import com.nashtech.rookies.ecommerce.models.cart.Cart;
import com.nashtech.rookies.ecommerce.repositories.cart.CartItemRepository;
import com.nashtech.rookies.ecommerce.repositories.cart.CartRepository;
import com.nashtech.rookies.ecommerce.repositories.user.UserRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.cart.CartService;

@Service
@Transactional(readOnly = true)
public class CartServiceImpl extends CommonServiceImpl<Cart, Long> implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper, CartItemRepository cartItemRepository, UserRepository userRepository) {
        super(cartRepository);
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    @Transactional
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO) {
        if (userRepository.existsById(cartRequestDTO.userId())) {
            Cart cart = new Cart();
            cart.setUser(userRepository.findById(cartRequestDTO.userId()).get());
            cart.setCartItems(new HashSet<>());
            cart.setQuantity(0L);
            cart = cartRepository.saveAndFlush(cart);
            return new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                    cart.getCartItems() != null ?
                            cart.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>());
        } else throw new NotFoundException("Not found User with an id: " + cartRequestDTO.userId());
    }

    @Override
    public ResponseEntity<?> handleGetCart(CartGetRequestParamsDTO requestParamsDTO) {
        CartResponseDTO cartResponseDTO;
        List<CartResponseDTO> cartResponseDTOs;

        if (requestParamsDTO.id() != null) {
            cartResponseDTO = getCartByCartId(requestParamsDTO.id());
            return ResponseEntity.ok(cartResponseDTO);
        } else if (requestParamsDTO.userId() != null) {
            cartResponseDTO = getCartByUserId(requestParamsDTO.userId());
            return ResponseEntity.ok(cartResponseDTO);
        } else {
            cartResponseDTOs = getCart();
            return ResponseEntity.ok(cartResponseDTOs);
        }
    }

    public List<CartResponseDTO> getCart() {
        var carts = cartRepository.findAll();
        List<CartResponseDTO> cartResponseDTOs = new ArrayList<>();
        carts.forEach(cart -> {
            Set<CartItem> cartItems = cart.getCartItems() != null ? cart.getCartItems() : new HashSet<>();
            cartResponseDTOs.add(new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                    cartItems.stream().map(Persistable::getId).collect(Collectors.toSet())));
        });
        return cartResponseDTOs;
    }

    public CartResponseDTO getCartByCartId(Long id) {
        if (cartRepository.existsById(id)) {
            Cart cart = cartRepository.findById(id).get();
            return new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                    cart.getCartItems() != null ?
                            cart.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>());
        } else throw new NotFoundException("Not found a Cart with an id: " + id);
    }

    public CartResponseDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            return new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                    cart.getCartItems() != null ?
                            cart.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>());
        } else throw new NotFoundException("Not found a Cart with an id: " + userId);
    }

    @Override
    @Transactional
    public CartResponseDTO updateCart(Long id, CartRequestDTO cartRequestDTO) {
        if (cartRepository.existsById(id)) {
            Cart cart = cartRepository.findById(id).get();
            if (userRepository.existsById(cartRequestDTO.userId())) {
                cart.setCartItems(cartRequestDTO.cartItem()
                        .stream()
                        .map(cartItem -> cartItemRepository.findById(cartItem).get()).collect(Collectors.toSet()));
                cart.setUser(userRepository.findById(cartRequestDTO.userId()).get());
                cart = cartRepository.saveAndFlush(cart);
                return new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                        cart.getCartItems() != null ?
                                cart.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>());
            } else throw new NotFoundException("Not found Cart with an id: " + id);
        } else throw new NotFoundException("Not found User with an id: " + cartRequestDTO.userId());
    }
}
