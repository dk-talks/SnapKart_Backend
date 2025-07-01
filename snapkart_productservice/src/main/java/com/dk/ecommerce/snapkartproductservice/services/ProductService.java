package com.dk.ecommerce.snapkartproductservice.services;

import com.dk.ecommerce.snapkartproductservice.exceptions.ProductNotFoundException;
import com.dk.ecommerce.snapkartproductservice.models.Product;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;
import java.util.Map;


public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(String id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> searchProducts(String query);
    Product updateProduct(String id, Map<String, Object> updates) throws ProductNotFoundException, JsonMappingException;
    void deleteProduct(String id) throws ProductNotFoundException;
}
