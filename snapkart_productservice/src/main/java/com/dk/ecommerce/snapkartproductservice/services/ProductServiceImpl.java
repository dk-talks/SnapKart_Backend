package com.dk.ecommerce.snapkartproductservice.services;

import com.dk.ecommerce.snapkartproductservice.exceptions.ProductNotFoundException;
import com.dk.ecommerce.snapkartproductservice.models.Product;
import com.dk.ecommerce.snapkartproductservice.repositories.ProductRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(String id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    public Product updateProduct(String id, Map<String, Object> updates) throws ProductNotFoundException, JsonMappingException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));


        try {
            Product patchedProduct = objectMapper.updateValue(product, updates);
            return productRepository.save(patchedProduct);
        } catch (IllegalArgumentException | JsonMappingException e) {
            throw new JsonMappingException("Error updating product with id: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while updating product with id: " + id, e);
        }
    }

    @Override
    public void deleteProduct(String id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
