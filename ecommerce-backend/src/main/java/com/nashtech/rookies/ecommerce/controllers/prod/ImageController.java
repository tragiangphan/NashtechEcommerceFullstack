package com.nashtech.rookies.ecommerce.controllers.prod;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.ImageService;

import jakarta.validation.Valid;

@RestController
public class ImageController extends RestVersion {
  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @PostMapping("/images")
  public ResponseEntity<ImageResponseDTO> createProdImage(@RequestBody @Valid ImageRequestDTO imageRequestDTO) {
    return ResponseEntity.ok(imageService.createImage(imageRequestDTO));
  }

  @GetMapping("/images")
  public ResponseEntity<List<ImageResponseDTO>> getImage(@RequestParam(name = "id", required = false) Long id) {
    List<ImageResponseDTO> imageResponseDTO;

    if (id != null) {
      imageResponseDTO = imageService.getImages(id);
    } else {
      imageResponseDTO = imageService.getImages();
    }
    return ResponseEntity.ok(imageResponseDTO);
  }

  @PutMapping("/images")
  public ResponseEntity<ImageResponseDTO> updateProdImageById(@RequestParam(name = "id", required = true) Long id,
      @RequestBody ImageRequestDTO imageRequestDTO) {
    return ResponseEntity.ok(imageService.updateImage(id, imageRequestDTO));
  }
}
