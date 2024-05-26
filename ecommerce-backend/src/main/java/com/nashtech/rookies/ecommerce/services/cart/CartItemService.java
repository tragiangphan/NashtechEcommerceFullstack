package com.nashtech.rookies.ecommerce.services.cart;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartItemRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartItemResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.CartItem;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface CartItemService extends CommonService<CartItem, Long> {
  CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO);

  List<CartItemResponseDTO> getCartItem();

  List<CartItemResponseDTO> getCartItem(Long id);

  CartItemResponseDTO updateCartItem(Long id, CartItemRequestDTO cartItemRequestDTO);

  String deleteCartItem(Long id);
}