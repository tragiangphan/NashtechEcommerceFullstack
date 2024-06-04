package com.nashtech.rookies.ecommerce.services.cart;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartItemGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.cart.requests.CartItemRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartItemResponseDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.PaginationCartItemDTO;
import com.nashtech.rookies.ecommerce.models.cart.CartItem;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

public interface CartItemService extends CommonService<CartItem, Long> {
  CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO);

  ResponseEntity<?> handleGetCartItem(CartItemGetRequestParamsDTO requestParamsDTO);

  CartItemResponseDTO updateCartItem(Long id, CartItemRequestDTO cartItemRequestDTO);

  String deleteCartItem(Long id);
}
