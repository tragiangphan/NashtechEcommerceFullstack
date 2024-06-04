package com.nashtech.rookies.ecommerce.services.cart;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.cart.requests.CartRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Cart;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.http.ResponseEntity;

public interface CartService extends CommonService<Cart, Long> {
  CartResponseDTO createCart(CartRequestDTO cartRequestDTO);

  ResponseEntity<?> handleGetCart(CartGetRequestParamsDTO requestParamsDTO);

  CartResponseDTO updateCart(Long id, CartRequestDTO cartRequestDTO);
}
