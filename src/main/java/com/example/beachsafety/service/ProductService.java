package com.example.beachsafety.service;

import com.example.beachsafety.entity.Product;
import com.example.beachsafety.entity.ProductType;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByType(ProductType type) {
        return productRepository.findByType(type);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    public Product createProduct(Product product) {
        if (product.getActive() == null) {
            product.setActive(true);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setType(updatedProduct.getType());
        existing.setDescription(updatedProduct.getDescription());
        existing.setUnitPrice(updatedProduct.getUnitPrice());
        if (updatedProduct.getActive() != null) {
            existing.setActive(updatedProduct.getActive());
        }
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }
}
