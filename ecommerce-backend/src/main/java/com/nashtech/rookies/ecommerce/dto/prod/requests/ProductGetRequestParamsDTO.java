package com.nashtech.rookies.ecommerce.dto.prod.requests;

import com.nashtech.rookies.ecommerce.models.constants.FeatureModeEnum;
import org.springframework.data.domain.Sort;

public record ProductGetRequestParamsDTO(Long id,
                                         String productName, String categoryName,
                                         FeatureModeEnum featureMode,
                                         Long maxPrice, Long minPrice,
                                         Sort.Direction dir,
                                         Integer pageNum, Integer pageSize) {
}
