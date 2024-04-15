package com.example.demo.services;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<Product> getAllProduct(Pageable pageable);
    Product insertProduct(ProductDTO productDTO);
    void deleteProduct(Long productId);
    Product getProductById(Long productId) throws Exception;
    Product updateProduct(ProductDTO productDTO, Long productId) throws Exception;
}
