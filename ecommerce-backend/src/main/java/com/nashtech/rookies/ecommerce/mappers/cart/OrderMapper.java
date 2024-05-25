package com.nashtech.rookies.ecommerce.mappers.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.cart.requests.OrderRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.OrderResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  @Mapping(target = "userId", source = "order.user.id")
  OrderRequestDTO toRequestDTO(Order order);

  @Mapping(target = "userId", source = "order.user.id")
  @Mapping(target = "createOn", source = "order.createdOn")
  @Mapping(target = "lastUpdateOn", source = "order.lastUpdatedOn")
  OrderResponseDTO toResponseDTO(Order order);

  Order toRequestEntity(OrderRequestDTO orderRequestDTO);
  Order toResponseEntity(OrderResponseDTO orderResponseDTO);
}
