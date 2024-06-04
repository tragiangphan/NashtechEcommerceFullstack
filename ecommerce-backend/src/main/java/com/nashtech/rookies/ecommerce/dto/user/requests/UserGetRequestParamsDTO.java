package com.nashtech.rookies.ecommerce.dto.user.requests;

import org.springframework.data.domain.Sort;

public record UserGetRequestParamsDTO(Long id, String username, Sort.Direction dir,
                                      Integer pageNum, Integer pageSize) {
}
