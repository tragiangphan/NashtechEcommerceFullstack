package com.nashtech.rookies.ecommerce.controllers.cart;

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
@RequestMapping(RestVersionConfig.API_VERSION + "/cart")
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
  public ResponseEntity<List<CartResponseDTO>> getCart(@RequestParam(name = "id", required = false) Long id) {
    List<CartResponseDTO> cartResponseDTOs;

    if (id != null) {
      cartResponseDTOs = cartService.getCart(id);
    } else {
      cartResponseDTOs = cartService.getCart();
    }
    return ResponseEntity.ok(cartResponseDTOs);
  }

  @PutMapping()
  public ResponseEntity<CartResponseDTO> updateCart(@RequestParam(name = "id", required = true) Long id,
      @RequestBody CartRequestDTO cartRequestDTO) {
    return ResponseEntity.ok(cartService.updateCart(id, cartRequestDTO));
  }
}
