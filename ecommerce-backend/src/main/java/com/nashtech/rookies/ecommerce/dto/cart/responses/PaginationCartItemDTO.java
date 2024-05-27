package com.nashtech.rookies.ecommerce.dto.cart.responses;

import java.util.List;

public record PaginationCartItemDTO (Integer totalPage, Long totalElement, Integer pageSize, Integer pageNum,
                                     List<CartItemResponseDTO> cartItems) {
}
