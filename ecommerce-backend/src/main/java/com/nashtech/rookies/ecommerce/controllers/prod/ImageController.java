package com.nashtech.rookies.ecommerce.controllers.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageGetRequestParamsDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.ImageService;

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
            @RequestParam("imageDesc") String imageDesc,
            @RequestParam("imageFile") MultipartFile multipartFile,
            @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(imageService.createImage(imagePath, multipartFile, productId, imageDesc));
    }

    @GetMapping()
    public ResponseEntity<?> getImage(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "productId", required = false) Long productId,
            @RequestParam(name = "direction", required = false) Sort.Direction dir,
            @RequestParam(name = "pageNum", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return imageService.handleGetImage(new ImageGetRequestParamsDTO(id, productId, dir, pageNum, pageSize));
    }

    @PutMapping()
    public ResponseEntity<ImageResponseDTO> updateProdImageById(
            @RequestParam("id") Long id,
            @RequestParam("productId") Long productId,
            @RequestParam("imageDesc") String imageDesc,
            @RequestParam("imageFile") MultipartFile multipartFile,
            @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(imageService.updateImage(id, imagePath, multipartFile, productId, imageDesc));
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteProdImageById(
            @RequestParam("id") Long id,
            @RequestHeader HttpHeaders headers) {
        return imageService.deleteImage(id);
    }
}
