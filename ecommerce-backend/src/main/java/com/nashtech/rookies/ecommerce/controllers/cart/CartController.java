package com.nashtech.rookies.ecommerce.controllers.cart;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartGetRequestParamsDTO;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.cart.requests.CartRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartResponseDTO;
import com.nashtech.rookies.ecommerce.services.cart.CartService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/carts")
public class CartController {
  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping()
  public ResponseEntity<CartResponseDTO> createCart(@RequestBody CartRequestDTO cartRequestDTO) {
    return ResponseEntity.ok(cartService.createCart(cartRequestDTO));
  }

  @GetMapping()
  public ResponseEntity<?> getCart(@RequestParam(name = "id", required = false) Long id,
                                   @RequestParam(name = "userId", required = false) Long userId) {

    return ResponseEntity.ok(cartService.handleGetCart(new CartGetRequestParamsDTO(id, userId)));
  }

  @PutMapping()
  public ResponseEntity<CartResponseDTO> updateCart(@RequestParam(name = "id", required = true) Long id,
      @RequestBody CartRequestDTO cartRequestDTO) {
    return ResponseEntity.ok(cartService.updateCart(id, cartRequestDTO));
  }
}
