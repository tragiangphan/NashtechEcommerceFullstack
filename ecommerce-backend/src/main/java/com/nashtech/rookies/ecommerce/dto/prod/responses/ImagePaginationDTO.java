package com.nashtech.rookies.ecommerce.dto.prod.responses;

import java.util.List;

public record ImagePaginationDTO(
    Integer totalPage, Long totalElement,
    Integer pageSize, Integer pageNum,
    List<ImageResponseDTO> images) {

}
