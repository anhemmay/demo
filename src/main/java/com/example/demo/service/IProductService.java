package com.example.demo.service;

import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.request.ProductDTO;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    Page<Product> getAllProducts(FilterRequest filterRequest, Pageable pageable);
    Product insertProduct(ProductDTO productDTO) throws Exception;
    void deleteProduct(Long productId) throws Exception;
    Product getProductById(Long productId) throws Exception;
    Product updateProduct(ProductDTO productDTO, Long productId) throws Exception;
}
