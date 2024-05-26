package com.nashtech.rookies.ecommerce.services.prod.impl;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ImageRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.models.prods.Image;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.models.prods.Supplier;
import com.nashtech.rookies.ecommerce.repositories.prod.ImageRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.SupplierRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceImplTest {
    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ImageServiceImpl imageServiceImpl;

    private Image image;
    private ImageRequestDTO imageRequestDTO;

    @BeforeEach
    void setUp() {
        image = new Image();
        image.setImageLink("https://nashtech.rookies.com/deridemouse.png");
        image.setImageDesc("Image description");
        image.setProduct(new Product());
    }

    @Test
    void createImage() {
    }

    @Test
    void getImages() {
    }

    @Test
    void testGetImages() {
    }

    @Test
    void updateImage() {
    }
}