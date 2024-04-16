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

@Query("SELECT p FROM Product p " +
        "WHERE p.status = :status " +
        "AND (:productCode IS NULL OR EXISTS (SELECT pd FROM ProductDetail pd WHERE pd.product = p AND pd.productCode LIKE %:productCode%)) " +
        "AND (:name IS NULL OR p.name LIKE %:name% OR EXISTS (SELECT pd FROM ProductDetail pd WHERE pd.product = p AND pd.name LIKE %:name%)) " +
        "AND (:cycle IS NULL OR EXISTS (SELECT pd FROM ProductDetail pd WHERE pd.product = p AND pd.cycle IN (:cycle))) " +
        "AND (:type IS NULL OR EXISTS (SELECT t FROM Type t WHERE t.product = p AND t.name = :type)) " +
        "AND (:minPrice IS NULL AND :maxPrice IS NULL OR EXISTS (SELECT pd FROM ProductDetail pd WHERE pd.product = p AND pd.price BETWEEN :minPrice AND :maxPrice))"
)
    Page<Product> findAllProduct(
            @Param("status") Boolean status,
            @Param("productCode") String productCode,
            @Param("name") String name,
            @Param("cycle") List<String> cycle,
            @Param("type") String type,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            Pageable pageable
    );
}
