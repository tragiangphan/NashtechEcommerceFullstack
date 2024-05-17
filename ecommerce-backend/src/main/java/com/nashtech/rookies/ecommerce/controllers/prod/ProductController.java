package com.nashtech.rookies.ecommerce.controllers.prod;

import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.prod.requests.ProductRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.ProductResponseDTO;
import com.nashtech.rookies.ecommerce.mappers.prod.ProductMapper;
import com.nashtech.rookies.ecommerce.models.prod.Product;
import com.nashtech.rookies.ecommerce.services.prod.ProductService;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
public class ProductController extends RestVersion {
  private final ProductService productService;
  private final ProductMapper productMapper;

  public ProductController(ProductService productService, ProductMapper productMapper) {
    this.productService = productService;
    this.productMapper = productMapper;
  }

  @PostMapping("/products")
  public ResponseEntity<Void> createProduct(@RequestBody ProductRequestDTO productDTO) {
    Product product = productService.createProductManual(productDTO);
    return ResponseEntity.created(URI.create("api/v1/products/" + product.getId())).build();
  }

  @GetMapping("/products")
  public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
    var products = productService.findAll();
    var productResponseDTO = new LinkedList<ProductResponseDTO>();
    for (var product : products) {
      ProductResponseDTO productDTO = productMapper.toResponseDTO(product);
      productResponseDTO.add(productDTO);
    }
    return ResponseEntity.ok(productResponseDTO);
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") Long id) {
    Product product = productService.findOne(id).orElseThrow(IllegalArgumentException::new);
    ProductResponseDTO productDTO = productMapper.toResponseDTO(product);
    return ResponseEntity.ok(productDTO);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<ProductResponseDTO> updateProductById(@PathVariable("id") Long id,
      @RequestBody ProductRequestDTO productRequestDTO) {
    Product product = productService.findOne(id).orElseThrow(IllegalArgumentException::new);
    product = productService.updateProductManual(product, productRequestDTO);
    return ResponseEntity.ok(productMapper.toResponseDTO(productService.save(product)));
  }
}
