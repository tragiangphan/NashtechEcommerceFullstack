package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageGetRequestParamsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.ImageMapper;
import com.nashtech.rookies.ecommerce.models.prod.Image;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.repositories.prod.ImageRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.ImageService;

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
    public ImageResponseDTO createImage(ImageRequestDTO imgRequestDTO) {
        Image image = new Image();
        if (productRepository.existsById(imgRequestDTO.productId())) {
            Product product = productRepository.findById(imgRequestDTO.productId()).get();
            image.setImageLink(imgRequestDTO.imageLink());
            image.setImageDesc(imgRequestDTO.imageDesc());
            image.setProduct(product);
            image = imageRepository.saveAndFlush(image);
            return imageMapper.toResponseDTO(image);
        } else {
            throw new NotFoundException("Not found Product with an id: " + imgRequestDTO.productId());
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
    public ImageResponseDTO updateImage(Long id, ImageRequestDTO imgRequestDTO) {
        if (imageRepository.existsById(id)) {
            Image image = imageRepository.findById(id).get();
            image.setImageLink(imgRequestDTO.imageLink());
            image.setImageDesc(imgRequestDTO.imageDesc());
            if (productRepository.existsById(imgRequestDTO.productId())) {
                Product product = productRepository.findById(imgRequestDTO.productId()).get();
                image.setProduct(product);
                imageRepository.saveAndFlush(image);
                return imageMapper.toResponseDTO(image);
            } else {
                throw new NotFoundException("Not found Product with an id: " + id);
            }
        } else {
            throw new NotFoundException("Not found Image with an id: " + id);
        }
    }
}
