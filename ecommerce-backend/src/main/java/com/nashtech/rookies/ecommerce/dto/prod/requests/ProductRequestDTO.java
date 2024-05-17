package com.nashtech.rookies.ecommerce.dto.prod.requests;

import java.util.Set;

public record ProductRequestDTO(
    String productName, String productDesc,
    String unit, int price, long quantity,
    boolean isFeatured, Long categoryId, 
    Set<Long> suppliers) {
}
