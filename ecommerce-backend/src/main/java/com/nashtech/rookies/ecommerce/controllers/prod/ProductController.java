package com.nashtech.rookies.ecommerce.controllers.prod;

import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.ProductService;

import java.util.List;

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
  public ResponseEntity<List<ProductResponseDTO>> getProduct(@RequestParam(name = "id", required = false) Long id) {
    List<ProductResponseDTO> productResponseDTO;

    if (id != null) {
      productResponseDTO = productService.getProducts(id);
    } else {
      productResponseDTO = productService.getProducts();
    }
    return ResponseEntity.ok(productResponseDTO);
  }

  @PutMapping()
  public ResponseEntity<ProductResponseDTO> updateProduct(@RequestParam(name = "id", required = true) Long id,
      @RequestBody ProductRequestDTO productRequestDTO) {
    return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
  }
}
