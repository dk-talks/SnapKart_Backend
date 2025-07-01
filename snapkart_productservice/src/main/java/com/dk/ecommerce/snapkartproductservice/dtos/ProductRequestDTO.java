package com.dk.ecommerce.snapkartproductservice.dtos;

import com.dk.ecommerce.snapkartproductservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
    private String brand;
    private int stockQuantity;
    private boolean inStock;

    public Product toProduct() {
        Product product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setImageUrl(this.imageUrl);
        product.setCategory(this.category);
        product.setBrand(this.brand);
        product.setStockQuantity(this.stockQuantity);
        product.setInStock(this.inStock);
        return product;
    }
}
