package com.nashtech.rookies.ecommerce.services.prod;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Image;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface ImageService extends CommonService<Image, Long> {
  ImageResponseDTO createImage(ImageRequestDTO imgRequestDTO);

  List<ImageResponseDTO> getImages();

  List<ImageResponseDTO> getImages(Long id);

  ImageResponseDTO updateImage(Long id, ImageRequestDTO imgRequestDTO);
}
