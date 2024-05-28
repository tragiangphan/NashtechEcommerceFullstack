package com.nashtech.rookies.ecommerce.dto.user.responses;

import java.util.List;

public record UserPaginationDTO(Integer totalPage, Long totalElement, Integer pageSize, Integer pageNum,
                                List<UserResponseDTO> users) {
}
