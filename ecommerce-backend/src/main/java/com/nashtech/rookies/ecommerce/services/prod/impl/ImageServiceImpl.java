package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageGetRequestParamsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImagePaginationDTO;
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
    public ImageResponseDTO createImage(String imagePathFile, MultipartFile multipartFile, Long productId,
            String imageDesc) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String filecode;

        try {
            filecode = ImageUploadUtil.saveFile(imagePathFile, fileName, multipartFile);

            ImageUploadResponse response = new ImageUploadResponse(
                    fileName, size, filecode + "-" + fileName);

            Image image = new Image(
                    imagePathFile + response.download(),
                    imageDesc,
                    productRepository.findById(productId).get());
            System.out.println("step6");

            image = imageRepository.saveAndFlush(image);
            System.out.println("step7");

            return imageMapper.toResponseDTO(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @Override
    public ResponseEntity<?> handleGetImage(ImageGetRequestParamsDTO requestParamsDTO) {
        ImagePaginationDTO imagePaginationDTOs;
        ImageResponseDTO imageResponseDTO;

        if (requestParamsDTO.id() != null) {
            imageResponseDTO = getImageById(requestParamsDTO.id());
            return ResponseEntity.ok(imageResponseDTO);
        } else if (requestParamsDTO.productId() != null) {
            imagePaginationDTOs = getImageByProductId(requestParamsDTO.productId(), requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1,
                    requestParamsDTO.pageSize());
            return ResponseEntity.ok(imagePaginationDTOs);
        } else {
            imagePaginationDTOs = getImages(
                    requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1,
                    requestParamsDTO.pageSize());
            return ResponseEntity.ok(imagePaginationDTOs);
        }
    }

    public ImagePaginationDTO getImages(Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Image> images = imageRepository.findAll(pageable);
        List<ImageResponseDTO> imageResponseDTOs = new ArrayList<>();
        images.forEach(image -> imageResponseDTOs.add(imageMapper.toResponseDTO(image)));
        return new ImagePaginationDTO(images.getTotalPages(), images.getTotalElements(), images.getSize(),
                images.getNumber(), imageResponseDTOs);
    }

    public ImagePaginationDTO getImageByProductId(Long productId, Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Image> images = imageRepository.findAllByProductId(productId, pageable);
        List<ImageResponseDTO> imageResponseDTOs = new ArrayList<>();
        images.forEach(image -> imageResponseDTOs.add(imageMapper.toResponseDTO(image)));
        return new ImagePaginationDTO(images.getTotalPages(), images.getTotalElements(), images.getSize(),
                images.getNumber(), imageResponseDTOs);
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
    public ImageResponseDTO updateImage(Long id, String imagePathFile, MultipartFile multipartFile, Long productId,
            String imageDesc) {
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
                image.setImageDesc(imageDesc);
                if (productRepository.existsById(productId)) {
                    Product product = productRepository.findById(productId).get();
                    image.setProduct(product);
                    imageRepository.saveAndFlush(image);
                    return imageMapper.toResponseDTO(image);
                } else {
                    throw new NotFoundException("Not found Product with an id: " + productId);
                }
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
            }
        } else {
            throw new NotFoundException("Not found Image with an id: " + id);
        }
    }
}
