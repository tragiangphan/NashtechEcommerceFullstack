package com.nashtech.rookies.ecommerce.controllers.cart;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartItemGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.PaginationCartItemDTO;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.cart.requests.CartItemRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartItemResponseDTO;
import com.nashtech.rookies.ecommerce.services.cart.CartItemService;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping()
    public ResponseEntity<CartItemResponseDTO> createCart(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return ResponseEntity.ok(cartItemService.createCartItem(cartItemRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getCart(@RequestParam(name = "id", required = false) Long id,
                                     @RequestParam(name = "userId") Long userId,
                                     @RequestParam(name = "direction") Sort.Direction dir,
                                     @RequestParam(name = "pageNum") Integer pageNum,
                                     @RequestParam(name = "pageSize") Integer pageSize) {
        return cartItemService.handleGetCartItem(new CartItemGetRequestParamsDTO(id, userId, dir, pageNum, pageSize));
    }

    @PutMapping()
    public ResponseEntity<CartItemResponseDTO> updateCart(@RequestParam(name = "id") Long id,
                                                          @RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return ResponseEntity.ok(cartItemService.updateCartItem(id, cartItemRequestDTO));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCart(@RequestParam(name = "id", required = true) Long id) {
        return ResponseEntity.ok(cartItemService.deleteCartItem(id));
    }
}
