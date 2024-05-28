package com.nashtech.rookies.ecommerce.services.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Image;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.http.ResponseEntity;

public interface ImageService extends CommonService<Image, Long> {
  ImageResponseDTO createImage(ImageRequestDTO imgRequestDTO);

  ResponseEntity<?> handleGetImage(ImageGetRequestParamsDTO requestParamsDTO);

  ImageResponseDTO updateImage(Long id, ImageRequestDTO imgRequestDTO);
}
