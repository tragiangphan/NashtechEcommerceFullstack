package com.nashtech.rookies.ecommerce.controllers.prod;

import java.util.LinkedList;
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
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.ImageMapper;
import com.nashtech.rookies.ecommerce.models.prod.Image;
import com.nashtech.rookies.ecommerce.services.prod.ImageService;

import jakarta.validation.Valid;

@RestController
public class ImageController extends RestVersion {
  private final ImageService imageService;
  private final ImageMapper imageMapper;

  public ImageController(ImageService imageService, ImageMapper imageMapper) {
    this.imageService = imageService;
    this.imageMapper = imageMapper;
  }

  @PostMapping("/images")
  public ResponseEntity<ImageResponseDTO> createProdImage(@RequestBody @Valid ImageRequestDTO prodImgRequestDTO) {
    return ResponseEntity.ok(imageService.createImage(prodImgRequestDTO));
  }

  @GetMapping("/images")
  public ResponseEntity<List<ImageResponseDTO>> getImage(@RequestParam(name = "id", required = false) Long id) {
    var imageResponseDTO = new LinkedList<ImageResponseDTO>();
    if (id != null) {
      Image productImage = imageService.findOne(id).orElseThrow(ResourceNotFoundException::new);
      imageResponseDTO.add(imageMapper.toResponseDTO(productImage));
    } else {
      var images = imageService.findAll();
      for (var image : images) {
        ImageResponseDTO prodImageDTO = imageMapper.toResponseDTO(image);
        imageResponseDTO.add(prodImageDTO);
      }
    }
    return ResponseEntity.ok(imageResponseDTO);
  }

  @PutMapping("/images")
  public ResponseEntity<ImageResponseDTO> updateProdImageById(@RequestParam(name = "id", required = true) Long id,
      @RequestBody ImageRequestDTO imageRequestDTO) {
    return ResponseEntity.ok(imageService.updateImage(id, imageRequestDTO));
  }
}
