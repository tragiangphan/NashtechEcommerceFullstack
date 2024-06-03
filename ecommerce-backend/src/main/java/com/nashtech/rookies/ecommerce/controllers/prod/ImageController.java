package com.nashtech.rookies.ecommerce.controllers.prod;

import java.io.IOException;
import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageGetRequestParamsDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageUploadResponse;
import com.nashtech.rookies.ecommerce.services.prod.ImageService;
import com.nashtech.rookies.ecommerce.utils.ImageUploadUtil;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/images")
public class ImageController {
    private final ImageService imageService;

    private final String imagePath;

    public ImageController(ImageService imageService, @Value("${app.image.folder}") String imagePath) {
        this.imageService = imageService;
        this.imagePath = imagePath;
    }

    @PostMapping()
    ResponseEntity<ImageResponseDTO> uploadFile(
            @RequestParam("productId") Long productId,
            @RequestParam("file") MultipartFile multipartFile,
            @RequestHeader HttpHeaders headers,
            @RequestBody ImageRequestDTO imageRequestDTO) {
        return ResponseEntity.ok(imageService.createImage(imagePath, multipartFile, imageRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getImage(@RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "productId", required = false) Long productId) {
        return imageService.handleGetImage(new ImageGetRequestParamsDTO(id, productId));
    }

    @PutMapping()
    public ResponseEntity<ImageResponseDTO> updateProdImageById(
            @RequestParam("id") Long id,
            @RequestParam("productId") Long productId,
            @RequestParam("file") MultipartFile multipartFile,
            @RequestHeader HttpHeaders headers,
            @RequestBody ImageRequestDTO imageRequestDTO) {
        return ResponseEntity.ok(imageService.updateImage(id, imagePath, multipartFile, imageRequestDTO));
    }
}
