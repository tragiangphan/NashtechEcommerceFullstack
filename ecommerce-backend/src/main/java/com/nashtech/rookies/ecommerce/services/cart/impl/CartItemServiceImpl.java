package com.nashtech.rookies.ecommerce.services.cart.impl;

import java.util.ArrayList;
import java.util.List;

import com.nashtech.rookies.ecommerce.repositories.cart.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartItemRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartItemResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.cart.CartItemMapper;
import com.nashtech.rookies.ecommerce.models.cart.CartItem;
import com.nashtech.rookies.ecommerce.repositories.cart.CartItemRepository;
import com.nashtech.rookies.ecommerce.repositories.cart.CartRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.cart.CartItemService;

@Service
@Transactional(readOnly = true)
public class CartItemServiceImpl extends CommonServiceImpl<CartItem, Long> implements CartItemService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper,
                               CartRepository cartRepository, ProductRepository productRepository) {
        super(cartItemRepository);
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    @Transactional
    public CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO) {
        CartItem cartItem = new CartItem();
        if (cartRepository.existsById(cartItemRequestDTO.cartId())) {
            cartItem.setCart(cartRepository.findById(cartItemRequestDTO.cartId()).get());
            if (productRepository.existsById(cartItemRequestDTO.productId())) {
                cartItem.setProduct(productRepository.findById(cartItemRequestDTO.productId()).get());
                cartItem.setQuantity(cartItemRequestDTO.quantity());
                cartItem = cartItemRepository.saveAndFlush(cartItem);
                return cartItemMapper.toResponseDTO(cartItem);
            } else
                throw new ResourceNotFoundException("Not found Product with an id: " + cartItemRequestDTO.productId());
        } else
            throw new ResourceNotFoundException("Not found Cart with an id: " + cartItemRequestDTO.cartId());
    }

//    new CartItemResponseDTO(cartItem.getId(), cartItem.getCreatedOn(), cartItem.getLastUpdatedOn(),
//                            cartItem.getQuantity(), cartItem.getCart().getId(), cartItem.getOrder().getId(),
//                            cartItem.getProduct().getId())

    @Override
    public List<CartItemResponseDTO> getCartItem() {
        var cartItems = cartItemRepository.findAll();
        List<CartItemResponseDTO> cartItemResponseDTOs = new ArrayList<>();
        cartItems.forEach(cartItem -> cartItemResponseDTOs.add(cartItemMapper.toResponseDTO(cartItem)));
        return cartItemResponseDTOs;
    }

    @Override
    public List<CartItemResponseDTO> getCartItem(Long id) {
        if (cartItemRepository.existsById(id)) {
            CartItem cartItem = cartItemRepository.findById(id).get();
            List<CartItemResponseDTO> cartItemResponseDTOs = new ArrayList<>();
            cartItemResponseDTOs.add(cartItemMapper.toResponseDTO(cartItem));
            return cartItemResponseDTOs;
        } else
            throw new ResourceNotFoundException("Not found a Cart Item with an id: " + id);
    }

    @Override
    @Transactional
    public CartItemResponseDTO updateCartItem(Long id, CartItemRequestDTO cartItemRequestDTO) {
        if (cartItemRepository.existsById(id)) {
            CartItem cartItem = cartItemRepository.findById(id).get();
            cartItem = cartItemRepository.saveAndFlush(cartItem);
            return cartItemMapper.toResponseDTO(cartItem);
        } else
            throw new ResourceNotFoundException("Not found a Cart Item with an id: " + id);
    }

    @Override
    @Transactional
    public String deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
        return "Delete Cart Item successful";
    }
}
