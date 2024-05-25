package com.nashtech.rookies.ecommerce.mappers.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
  @Mapping(target = "userId", source = "cart.user.id")
  CartRequestDTO toRequestDTO(Cart cart);

  @Mapping(target = "userId", source = "cart.user.id")
  @Mapping(target = "cartItems", ignore = true)
  CartResponseDTO toResponseDTO(Cart cart);

  @Mapping(target = "cartItems", ignore = true)
  Cart toRequesteEntity(CartRequestDTO cartRequestDTO);

  @Mapping(target = "cartItems", ignore = true)
  Cart toResponseEntity(CartResponseDTO cartResponseDTO);
}
