package com.example.demo.services;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Page<Product> getAllProduct(Boolean status, String productCode, String name, List<Integer> cycle, String type, Pageable pageable);
    Product insertProduct(ProductDTO productDTO) throws Exception;
    void deleteProduct(Long productId) throws Exception;
    Product getProductById(Long productId) throws Exception;
    Product updateProduct(ProductDTO productDTO, Long productId) throws Exception;
}
