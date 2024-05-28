package com.example.demo.listener;

import com.example.demo.model.Product;
import com.example.demo.service.IProductRedisService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ProductListener {
    private final IProductRedisService productRedisService;

    @PostPersist
    public void create(Product product){
        log.warn("post persist product");
        productRedisService.clear();
    }
    @PostUpdate
    public void update(Product product){
        log.warn("post update product");
        productRedisService.clear();
    }
}
