package com.example.inventory.service;

import com.example.inventory.dto.ProductRequest;
import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product(
            request.getSku(),
            request.getName(),
            request.getPrice(),
            request.getWeight(),
            request.getUnitSize()
        );
        product.setDescription(request.getDescription());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product product = getProductById(id);
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setWeight(request.getWeight());
        product.setUnitSize(request.getUnitSize());
        product.setDescription(request.getDescription());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
