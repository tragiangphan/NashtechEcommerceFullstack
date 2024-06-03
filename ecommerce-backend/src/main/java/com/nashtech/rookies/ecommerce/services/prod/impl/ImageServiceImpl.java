package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageGetRequestParamsDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageUploadResponse;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.ImageMapper;
import com.nashtech.rookies.ecommerce.models.prod.Image;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.repositories.prod.ImageRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.ImageService;
import com.nashtech.rookies.ecommerce.utils.ImageUploadUtil;

import jakarta.validation.constraints.NotBlank;

@Service
@Transactional(readOnly = true)
public class ImageServiceImpl extends CommonServiceImpl<Image, Long> implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final ProductRepository productRepository;

    ImageServiceImpl(ImageRepository imageRepository, ProductRepository productRepository, ImageMapper imageMapper) {
        super(imageRepository);
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.productRepository = productRepository;
    }

    @Transactional
    public ImageResponseDTO createImage(String imagePathFile, MultipartFile multipartFile, ImageRequestDTO imgRequestDTO) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String filecode;
        try {
            filecode = ImageUploadUtil.saveFile(imagePathFile, fileName, multipartFile);
            ImageUploadResponse response = new ImageUploadResponse(
                    fileName, size, filecode + "-" + fileName);
            Image image = new Image(
                    imagePathFile + response.download(),
                    imgRequestDTO.imageDesc(),
                    productRepository.findById(imgRequestDTO.productId()).get());
            image = imageRepository.saveAndFlush(image);
            return imageMapper.toResponseDTO(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @Override
    public ResponseEntity<?> handleGetImage(ImageGetRequestParamsDTO requestParamsDTO) {
        List<ImageResponseDTO> imageResponseDTOs;
        ImageResponseDTO imageResponseDTO;

        if (requestParamsDTO.id() != null) {
            imageResponseDTO = getImageById(requestParamsDTO.id());
            return ResponseEntity.ok(imageResponseDTO);
        } else if (requestParamsDTO.productId() != null) {
            imageResponseDTOs = getImageByProductId(requestParamsDTO.productId());
            return ResponseEntity.ok(imageResponseDTOs);
        } else {
            imageResponseDTOs = getImages();
            return ResponseEntity.ok(imageResponseDTOs);
        }
    }

    public List<ImageResponseDTO> getImages() {
        var images = imageRepository.findAll();
        List<ImageResponseDTO> imageResponseDTOs = new ArrayList<>();
        images.forEach(image -> imageResponseDTOs.add(imageMapper.toResponseDTO(image)));
        return imageResponseDTOs;
    }

    public List<ImageResponseDTO> getImageByProductId(Long productId) {
        var images = imageRepository.findAll();
        List<ImageResponseDTO> imageResponseDTOs = new ArrayList<>();
        images.forEach(image -> imageResponseDTOs.add(imageMapper.toResponseDTO(image)));
        return imageResponseDTOs;
    }

    public ImageResponseDTO getImageById(Long id) {
        if (imageRepository.existsById(id)) {
            Image image = imageRepository.findById(id).get();
            return imageMapper.toResponseDTO(image);
        } else {
            throw new NotFoundException("Not found Image with an id: " + id);
        }
    }

    @Transactional
    public ImageResponseDTO updateImage(Long id, String imagePathFile, MultipartFile multipartFile,
            ImageRequestDTO imgRequestDTO) {
        if (imageRepository.existsById(id)) {
            Image image = imageRepository.findById(id).get();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            long size = multipartFile.getSize();

            String filecode;
            try {
                filecode = ImageUploadUtil.saveFile(imagePathFile, fileName,
                        multipartFile);
                ImageUploadResponse response = new ImageUploadResponse(
                        fileName, size, filecode + "-" + fileName);
                image.setImageLink(imagePathFile + response.download());
                image.setImageDesc(imgRequestDTO.imageDesc());
                if (productRepository.existsById(imgRequestDTO.productId())) {
                    Product product = productRepository.findById(imgRequestDTO.productId()).get();
                    image.setProduct(product);
                    imageRepository.saveAndFlush(image);
                    return imageMapper.toResponseDTO(image);
                } else {
                    throw new NotFoundException("Not found Product with an id: " + id);
                }
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
            }
        } else {
            throw new NotFoundException("Not found Image with an id: " + id);
        }
    }
}
