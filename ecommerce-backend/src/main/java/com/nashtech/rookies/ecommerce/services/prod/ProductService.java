package com.nashtech.rookies.ecommerce.services.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.http.ResponseEntity;

public interface ProductService extends CommonService<Product, Long> {
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    ResponseEntity<?> handleGetProduct(ProductGetRequestParamsDTO requestParamsDTO);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
}
