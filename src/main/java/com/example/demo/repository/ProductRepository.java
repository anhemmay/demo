package com.example.demo.repository;

import com.example.demo.dto.request.FilterRequest;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value =
            "SELECT p FROM Product p " +
                    "LEFT JOIN p.productDetails pd " +
                    "ON p.id = pd.product.id " +
                    "WHERE " +
                    "(:#{#request.status} IS NULL OR :#{#request.status} = p.status) AND " +
                    "(:#{#request.productCode} IS NULL OR :#{#request.productCode} = '' OR lower(pd.productCode) LIKE concat('%',lower(:#{#request.productCode}),'%')) AND " +
                    "(:#{#request.name} IS NULL OR :#{#request.name} = '' OR lower(p.name) LIKE concat('%',lower(:#{#request.name}),'%') OR lower(pd.name) LIKE concat('%',lower(:#{#request.name}),'%')) AND " +
                    "(:#{#request.minPrice} IS NULL OR :#{#request.maxPrice} IS NULL OR pd.price BETWEEN :#{#request.minPrice} AND :#{#request.maxPrice}) AND " +
                    "(:#{#request.cycles} IS NULL OR pd.cycle IN (:#{#request.cycles})) AND " +
                    "(:#{#request.types} IS NULL OR :#{#request.types} = ''  OR lower(p.types) LIKE concat('%',lower(:#{#request.types}),'%'))"
    )
    Page<Product> findByFilter(FilterRequest request, Pageable pageable);
}
