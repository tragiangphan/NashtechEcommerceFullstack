package com.nashtech.rookies.ecommerce.controllers.prod;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageGetRequestParamsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.ImageService;


@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
    public ResponseEntity<ImageResponseDTO> createProdImage(@RequestBody ImageRequestDTO imageRequestDTO) {
        return ResponseEntity.ok(imageService.createImage(imageRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getImage(@RequestParam(name = "id", required = false) Long id,
                                      @RequestParam(name = "productId", required = false) Long productId) {
        return imageService.handleGetImage(new ImageGetRequestParamsDTO(id, productId));
    }

    @PutMapping()
    public ResponseEntity<ImageResponseDTO> updateProdImageById(@RequestParam(name = "id") Long id,
                                                                @RequestBody ImageRequestDTO imageRequestDTO) {
        return ResponseEntity.ok(imageService.updateImage(id, imageRequestDTO));
    }
}
