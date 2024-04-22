package com.example.demo.repository;

import com.example.demo.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByProductId(Long productId);

    Boolean existsByProductCodeAndProduct_Operator(String productCode, String productOperator);
}
