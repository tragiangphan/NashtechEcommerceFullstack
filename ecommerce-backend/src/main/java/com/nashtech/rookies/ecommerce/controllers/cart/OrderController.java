package com.nashtech.rookies.ecommerce.controllers.cart;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.cart.requests.OrderRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.OrderResponseDTO;
import com.nashtech.rookies.ecommerce.services.cart.OrderService;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping()
  public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
    return ResponseEntity.ok(orderService.createOrder(orderRequestDTO));
  }

  @GetMapping()
  public ResponseEntity<List<OrderResponseDTO>> getOrder(@RequestParam(name = "id", required = false) Long id) {
    List<OrderResponseDTO> orderResponseDTOs;

    if (id != null) {
      orderResponseDTOs = orderService.getOrder(id);
    } else {
      orderResponseDTOs = orderService.getOrder();
    }
    return ResponseEntity.ok(orderResponseDTOs);
  }

  @PutMapping()
  public ResponseEntity<OrderResponseDTO> updateOrder(@RequestParam(name = "id", required = true) Long id,
      @RequestBody OrderRequestDTO orderRequestDTO) {
    return ResponseEntity.ok(orderService.updateOrder(id, orderRequestDTO));
  }
}
