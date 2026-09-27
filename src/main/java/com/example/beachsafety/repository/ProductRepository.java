package com.example.beachsafety.repository;

import com.example.beachsafety.entity.Product;
import com.example.beachsafety.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByType(ProductType type);
    List<Product> findByActiveTrue();
}
