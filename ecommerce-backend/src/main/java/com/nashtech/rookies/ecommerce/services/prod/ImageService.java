package com.nashtech.rookies.ecommerce.services.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.models.prod.Image;
import com.nashtech.rookies.ecommerce.services.CommonService;


public interface ImageService extends CommonService<Image, Long> {
  Image createNewImage(ImageRequestDTO imgRequestDTO);
  Image updateExistImage(Long id, ImageRequestDTO imgRequestDTO);
}
