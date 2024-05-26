package com.nashtech.rookies.ecommerce.services.prod;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductPaginationDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.models.prods.Product;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.data.domain.Sort;

public interface ProductService extends CommonService<Product, Long> {
  ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

  ProductPaginationDTO getProducts(Sort.Direction dir, int pageNum, int pageSize);

  ProductPaginationDTO getProducts(Long id);

  ProductPaginationDTO getProductByProductName(String productName, Sort.Direction dir, int pageNum, int pageSize);

  ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
}
