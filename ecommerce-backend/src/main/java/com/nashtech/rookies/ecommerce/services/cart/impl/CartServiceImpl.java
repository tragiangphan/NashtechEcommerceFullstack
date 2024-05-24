package com.nashtech.rookies.ecommerce.services.cart.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.nashtech.rookies.ecommerce.models.cart.CartItem;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
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
            cart.setQuantity();
            cart = cartRepository.saveAndFlush(cart);
            return new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                    cart.getCartItems() != null ?
                            cart.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>());
        } else throw new ResourceNotFoundException("Not found User with an id: " + cartRequestDTO.userId());
    }

    @Override
    public List<CartResponseDTO> getCart() {
        var carts = cartRepository.findAll();
        List<CartResponseDTO> cartResponseDTOs = new ArrayList<>();
        carts.forEach(cart -> cartResponseDTOs.add(new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                cart.getCartItems() != null ?
                        cart.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>())));
        return cartResponseDTOs;
    }

    @Override
    public List<CartResponseDTO> getCart(Long id) {
        if (cartRepository.existsById(id)) {
            Cart cart = cartRepository.findById(id).get();
            return List.of(new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                    cart.getCartItems() != null ?
                            cart.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>()));
        } else throw new ResourceNotFoundException("Not found a Cart with an id: " + id);
    }

    @Override
    @Transactional
    public CartResponseDTO updateCart(Long id, CartRequestDTO cartRequestDTO) {
        if (cartRepository.existsById(id)) {
            Cart cart = cartRepository.findById(id).get();
            if (userRepository.existsById(cartRequestDTO.userId())) {
                cart.setQuantity(cartRequestDTO.quantity());
                cart.setCartItems(cartRequestDTO.cartItem()
                        .stream()
                        .map(cartItem -> cartItemRepository.findById(cartItem).get()).collect(Collectors.toSet()));
                cart.setUser(userRepository.findById(cartRequestDTO.userId()).get());
                cart = cartRepository.saveAndFlush(cart);
                return new CartResponseDTO(cart.getId(), cart.getQuantity(), cart.getUser().getId(),
                        cart.getCartItems() != null ?
                                cart.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>());
            } else throw new ResourceNotFoundException("Not found Cart with an id: " + id);
        } else throw new ResourceNotFoundException("Not found User with an id: " + cartRequestDTO.userId());
    }
}
