package com.nashtech.rookies.ecommerce.controllers.prod;

import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductPaginationDTO;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController()
@RequestMapping(RestVersionConfig.API_VERSION + "/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getProduct(@RequestParam(name = "id", required = false) Long id,
                                                           @RequestParam(name = "productName", required = false) String productName,
                                                           @RequestParam(name = "categoryName", required = false) String categoryName,
                                                           @RequestParam(name = "maxPrice", required = false) Long maxPrice,
                                                           @RequestParam(name = "minPrice", required = false) Long minPrice,
                                                           @RequestParam(name = "direction") Sort.Direction dir,
                                                           @RequestParam(name = "pageNum") Integer pageNum,
                                                           @RequestParam(name = "pageSize") Integer pageSize) {
        return productService.handleGetProduct(new ProductGetRequestParamsDTO(id, productName,
                categoryName, maxPrice, minPrice, dir, pageNum, pageSize));
    }

    @PutMapping()
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestParam(name = "id") Long id,
                                                            @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
    }
}
