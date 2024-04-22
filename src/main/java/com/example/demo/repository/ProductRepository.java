package com.example.demo.repository;

import com.example.demo.dto.FilterRequest;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

//@Query("SELECT p FROM Product p " +
//        "WHERE p.status = :status " +
//        "AND (:productCode IS NULL OR EXISTS (SELECT pd FROM ProductDetail pd WHERE pd.product = p AND pd.productCode LIKE %:productCode%)) " +
//        "AND (:name IS NULL OR p.name LIKE %:name% OR EXISTS (SELECT pd FROM ProductDetail pd WHERE pd.product = p AND pd.name LIKE %:name%)) " +
//        "AND (:cycle IS NULL OR EXISTS (SELECT pd FROM ProductDetail pd WHERE pd.product = p AND pd.cycle IN (:cycle))) " +
//        "AND (:type IS NULL OR EXISTS (SELECT t FROM Type t WHERE t.product = p AND t.name = :type)) " +
//        "AND (:minPrice IS NULL AND :maxPrice IS NULL OR EXISTS (SELECT pd FROM ProductDetail pd WHERE pd.product = p AND pd.price BETWEEN :minPrice AND :maxPrice))"
//)
//    Page<Product> findAllProduct(
//            @Param("status") Boolean status,
//            @Param("productCode") String productCode,
//            @Param("name") String name,
//            @Param("cycle") List<String> cycle,
//            @Param("type") String type,
//            @Param("minPrice") Float minPrice,
//            @Param("maxPrice") Float maxPrice,
//            Pageable pageable
//    );

    @Query(value =
            "SELECT p FROM Product p " +
                    "LEFT JOIN ProductDetail pd " +
                    "WHERE " +
                    "(:#{#request.status} IS NULL OR :#{#request.status} = '' OR :#{#request.status} = p.status) "
//                    "(:#{#request.productCode} IS NULL OR :#{#request.productCode} = '' OR lower(pd.productCode) LIKE concat('%',lower(:#{#request.productCode}),'%')) AND " +
//                    "(:#{#request.name} IS NULL OR :#{#request.name} = '' OR lower(p.name) LIKE concat('%',lower(:#{#request.name}),'%') OR lower(pd.name) LIKE concat('%',lower(:#{#request.name}),'%')) AND " +
//                    "(:#{#request.minPrice} IS NULL OR :#{#request.maxPrice} IS NULL OR pd.price BETWEEN :#{#request.minPrice} AND :#{#request.maxPrice}) AND " +
//                    ":#{#request.cycles} IS NULL OR :#{#request.cycles} = '' OR pd.cycle IN :#{#request.cycles} AND " +
//                    ":#{#request.types} IS NULL OR :#{#request.types} = '' OR p.types IN :#{#request.types}"
    )
    Page<Product> findByFilter(FilterRequest request, Pageable pageable);
}
