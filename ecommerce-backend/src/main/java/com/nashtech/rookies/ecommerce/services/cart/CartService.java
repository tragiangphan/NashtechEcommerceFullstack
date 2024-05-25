package com.nashtech.rookies.ecommerce.services.cart;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Cart;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface CartService extends CommonService<Cart, Long> {
  CartResponseDTO createCart(CartRequestDTO cartRequestDTO);

  List<CartResponseDTO> getCart();

  List<CartResponseDTO> getCart(Long id);

  CartResponseDTO updateCart(Long id, CartRequestDTO cartRequestDTO);
}
