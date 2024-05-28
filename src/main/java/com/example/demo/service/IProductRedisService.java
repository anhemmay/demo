package com.example.demo.service;

import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.response.ProductPage;
import com.example.demo.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductRedisService {
    void clear();

    ProductPage getAllProducts(FilterRequest filterRequest, Pageable pageable) throws JsonProcessingException;
    void saveAllProducts(ProductPage products, FilterRequest filterRequest, Pageable pageable) throws JsonProcessingException;
}
