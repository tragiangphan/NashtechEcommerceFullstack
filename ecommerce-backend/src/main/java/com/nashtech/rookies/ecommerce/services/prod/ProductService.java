package com.nashtech.rookies.ecommerce.services.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface ProductService extends CommonService<Product, Long>{
  Product createProductManual(ProductRequestDTO productRequestDTO);
  Product updateProductManual(Product product, ProductRequestDTO productRequestDTO);
}
