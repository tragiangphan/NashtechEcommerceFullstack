package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ImageResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.ImageMapper;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Image;
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
    Product product = productRepository.findById(imgRequestDTO.productId()).orElseThrow(ResourceNotFoundException::new);
    image.setImageLink(imgRequestDTO.imageLink());
    image.setImageDesc(imgRequestDTO.imageDesc());
    image.setProduct(product);
    image = imageRepository.saveAndFlush(image);
    return imageMapper.toResponseDTO(image);
  }

  @Override
  public List<ImageResponseDTO> getImages() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getImages'");
  }

  @Override
  public List<ImageResponseDTO> getImages(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getImages'");
  }

  @Transactional
  public ImageResponseDTO updateImage(Long id, ImageRequestDTO imgRequestDTO) {
    Image image = imageRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    Product product = productRepository.findById(imgRequestDTO.productId()).orElseThrow(ResourceNotFoundException::new);
    image.setImageLink(imgRequestDTO.imageLink());
    image.setImageDesc(imgRequestDTO.imageDesc());
    image.setProduct(product);
    imageRepository.saveAndFlush(image);
    return imageMapper.toResponseDTO(image);
  }
}
