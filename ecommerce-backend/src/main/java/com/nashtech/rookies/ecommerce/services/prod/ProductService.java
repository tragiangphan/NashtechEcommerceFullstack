package com.nashtech.rookies.ecommerce.services.prod;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface ProductService extends CommonService<Product, Long> {
  ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

  List<ProductResponseDTO> getProducts();

  List<ProductResponseDTO> getProducts(Long id);

  ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
}
