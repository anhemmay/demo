package com.example.demo.service;

import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    Page<Product> getAllProducts(FilterRequest filterRequest, PageRequest pageRequest);
    Product insertProduct(ProductDTO productDTO) throws Exception;
    void deleteProduct(Long productId) throws Exception;
    Product getProductById(Long productId) throws Exception;
    Product updateProduct(ProductDTO productDTO, Long productId) throws Exception;
}
