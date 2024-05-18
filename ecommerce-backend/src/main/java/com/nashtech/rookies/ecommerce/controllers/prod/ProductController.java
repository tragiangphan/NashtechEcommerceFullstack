package com.nashtech.rookies.ecommerce.controllers.prod;

import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.prod.ProductMapper;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.services.prod.ProductService;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController()
public class ProductController extends RestVersion {
  private final ProductService productService;
  private final ProductMapper productMapper;

  public ProductController(ProductService productService, ProductMapper productMapper) {
    this.productService = productService;
    this.productMapper = productMapper;
  }

  @PostMapping("/products")
  public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productDTO) {
    return ResponseEntity.ok(productService.createProduct(productDTO));
  }

  @GetMapping("/products")
  public ResponseEntity<List<ProductResponseDTO>> getProduct(@RequestParam(name = "id", required = false) Long id) {
    var productResponseDTO = new LinkedList<ProductResponseDTO>();
    if (id != null) {
      Product product = productService.findOne(id).orElseThrow(ResourceNotFoundException::new);
      productResponseDTO.add(productMapper.toResponseDTO(product));
    } else {
      var products = productService.findAll();
      for (var product : products) {
        ProductResponseDTO productDTO = productMapper.toResponseDTO(product);
        productResponseDTO.add(productDTO);
      }
    }
    return ResponseEntity.ok(productResponseDTO);
  }

  @PutMapping("/products")
  public ResponseEntity<ProductResponseDTO> updateProduct(@RequestParam(name = "id", required = true) Long id,
      @RequestBody ProductRequestDTO productRequestDTO) {
    return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
  }
}
