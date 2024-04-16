package com.example.demo.repositories;

import com.example.demo.models.Product;
import com.example.demo.models.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p JOIN p.productDetails pd " +
            "JOIN p.type t " +
            "WHERE p.status = :status " +
            "AND (:productCode = '' OR pd.productCode LIKE %:productCode%) " +
            "AND (p.name LIKE %:name% OR pd.name LIKE %:name%) " +
            "AND pd.cycle IN :cycle " +
            "AND t.name = :type"
    )
    Page<Product> findAllProduct(
            @Param("status") Boolean status,
            @Param("productCode") String productCode,
            @Param("name") String name,
            @Param("cycle") List<Integer> cycle,
            @Param("type") String type,
            Pageable pageable
    );
}
