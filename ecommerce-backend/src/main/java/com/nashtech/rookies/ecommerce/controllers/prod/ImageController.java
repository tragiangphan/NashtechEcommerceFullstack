package com.nashtech.rookies.ecommerce.controllers.prod;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
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

  @PostMapping("/prodImages")
  public ResponseEntity<Void> createProdImage(@RequestBody @Valid ImageRequestDTO prodImgRequestDTO) {
    Image prodImage = imageService.createNewImage(prodImgRequestDTO);
    return ResponseEntity.created(URI.create("/api/v1/prodImages/" + prodImage.getId())).build();
  }

  @GetMapping("/prodImages")
  public ResponseEntity<List<ImageResponseDTO>> getAllProdImages() {
    var images = imageService.findAll();
    var imageResponseDTO = new LinkedList<ImageResponseDTO>();
    for (var image : images) {
      ImageResponseDTO prodImageDTO = imageMapper.toResponseDTO(image);
      imageResponseDTO.add(prodImageDTO);
    }
    return ResponseEntity.ok(imageResponseDTO);
  }

  @GetMapping("/prodImages/{id}")
  public ResponseEntity<ImageResponseDTO> getProImageById(@RequestParam("id") @PathVariable("id") Long id) {
    Image productImage = imageService.findOne(id).orElseThrow(IllegalArgumentException::new);
    ImageResponseDTO prodImgResponseDTO = imageMapper.toResponseDTO(productImage);
    return ResponseEntity.ok(prodImgResponseDTO);
  }

  @PutMapping("/prodImages/{id}")
  public ResponseEntity<ImageResponseDTO> updateProdImageById(@RequestParam("id") @PathVariable Long id,
      @RequestBody ImageRequestDTO imageRequestDTO) {
    Image productImage = imageService.updateExistImage(id, imageRequestDTO);
    return ResponseEntity.ok(imageMapper.toResponseDTO(productImage));
  }
}
