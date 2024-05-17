package com.nashtech.rookies.ecommerce.services.prod.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.models.prod.Image;
import com.nashtech.rookies.ecommerce.repositories.prod.ImageRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.ImageService;

@Service
@Transactional(readOnly = true)
public class ImageServiceImpl extends CommonServiceImpl<Image, Long> implements ImageService {

  private final ImageRepository prodImgRepository;
  private final ProductRepository prodRepository;

  ImageServiceImpl(ImageRepository prodImgRepository, ProductRepository prodRepository) {
    super(prodImgRepository);
    this.prodImgRepository = prodImgRepository;
    this.prodRepository = prodRepository;
  }

  @Transactional
  public Image createNewImage(ImageRequestDTO imgRequestDTO) {
    Image productImage = new Image();
    Product product = prodRepository.findById(imgRequestDTO.productId()).orElseThrow(NotFoundException::new);
    productImage.setImageLink(imgRequestDTO.imageLink());
    productImage.setImageDesc(imgRequestDTO.imageDesc());
    productImage.setProduct(product);
    prodImgRepository.saveAndFlush(productImage);
    return productImage;
  }

  @Transactional
  public Image updateExistImage(Long id, ImageRequestDTO imgRequestDTO) {
    Image productImage = prodImgRepository.findById(id).orElseThrow(NotFoundException::new);
    Product product = prodRepository.findById(imgRequestDTO.productId()).orElseThrow(NotFoundException::new);
    productImage.setImageLink(imgRequestDTO.imageLink());
    productImage.setImageDesc(imgRequestDTO.imageDesc());
    productImage.setProduct(product);
    prodImgRepository.saveAndFlush(productImage);
    return productImage;
  }
}
