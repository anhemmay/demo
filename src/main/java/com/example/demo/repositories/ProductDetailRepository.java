package com.example.demo.repositories;

import com.example.demo.models.Product;
import com.example.demo.models.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByProductId(Long productId);
}
