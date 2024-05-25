package com.nashtech.rookies.ecommerce.services.cart;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.cart.requests.OrderRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.OrderResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Order;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface OrderService extends CommonService<Order, Long> {
  OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

  List<OrderResponseDTO> getOrder();

  List<OrderResponseDTO> getOrder(Long id);

  OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO);
}
