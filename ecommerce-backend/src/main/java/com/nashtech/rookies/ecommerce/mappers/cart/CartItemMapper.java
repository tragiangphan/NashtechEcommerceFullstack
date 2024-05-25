package com.nashtech.rookies.ecommerce.mappers.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nashtech.rookies.ecommerce.dto.cart.requests.CartItemRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.CartItemResponseDTO;
import com.nashtech.rookies.ecommerce.models.cart.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
  @Mapping(target = "cartId", source = "cartItem.cart.id")
  @Mapping(target = "productId", source = "cartItem.product.id")
  CartItemRequestDTO toRequestDTO(CartItem cartItem);

  @Mapping(target = "cartId", source = "cartItem.cart.id")
  @Mapping(target = "productId", source = "cartItem.product.id")
  CartItemResponseDTO toResponseDTO(CartItem cartItem);

  // @Mapping(target = "cart", ignore = true)
  // @Mapping(target = "order", ignore = true)
  // @Mapping(target = "product", ignore = true)
  // CartItem toRequestEntity(CartItemRequestDTO cartDTO);

  // @Mapping(target = "cart", ignore = true)
  // @Mapping(target = "order", ignore = true)
  // @Mapping(target = "product", ignore = true)
  // CartItem toResponseEntity(CartItemResponseDTO cartDTO);
}