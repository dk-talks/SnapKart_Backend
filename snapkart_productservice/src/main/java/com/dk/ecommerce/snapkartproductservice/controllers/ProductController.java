package com.dk.ecommerce.snapkartproductservice.controllers;

import com.dk.ecommerce.snapkartproductservice.dtos.ProductRequestDTO;
import com.dk.ecommerce.snapkartproductservice.dtos.ProductResponseDTO;
import com.dk.ecommerce.snapkartproductservice.models.Product;
import com.dk.ecommerce.snapkartproductservice.services.ProductService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        ResponseEntity<Product> responseEntity;
        try {
            Product product = productService.getProductById(id);
            responseEntity = ResponseEntity.ok(product);
            return responseEntity;
        } catch (NoSuchElementException e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return responseEntity;
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return responseEntity;
        }
    }

    @GetMapping("/products/internal/{id}")
    public ResponseEntity<ProductResponseDTO> getProductByIdd(@PathVariable("id") String id) {
        try {
            Product product = productService.getProductById(id);
            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setId(product.getId());
            responseDTO.setName(product.getName());
            responseDTO.setPrice(product.getPrice());
            responseDTO.setStockQuantity(product.getStockQuantity());
            return ResponseEntity.ok(responseDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // create a new product
    @PostMapping("/products")
    public ResponseEntity<Boolean> createProduct(@RequestBody ProductRequestDTO requestDTO) {
        Product product = requestDTO.toProduct();
        ResponseEntity<Boolean> responseEntity;

        try {
            product = productService.createProduct(product);
            responseEntity = ResponseEntity.ok(true);
            return responseEntity;
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(500).body(false);
            return responseEntity;
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam("query") String query) {
        ResponseEntity<List<Product>> responseEntity;
        try {
            List<Product> products = productService.searchProducts(query);
            responseEntity = ResponseEntity.ok(products);
            return responseEntity;
        } catch (Exception e) {
            responseEntity = ResponseEntity.notFound().build();
            return responseEntity;
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("id") String id,
            @RequestBody Map<String, Object> updateFields) {
        try {
            Product updatedProduct = productService.updateProduct(id, updateFields);
            return ResponseEntity.ok("Product updated successfully: " + updatedProduct);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (IllegalArgumentException | JsonMappingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }



}
