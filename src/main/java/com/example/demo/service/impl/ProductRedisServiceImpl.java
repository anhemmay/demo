package com.example.demo.service.impl;

import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.response.ProductPage;
import com.example.demo.model.Product;
import com.example.demo.service.IProductRedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductRedisServiceImpl implements IProductRedisService {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;

    private String getKeyFrom(FilterRequest filterRequest, Pageable pageable){
        String filterRequestString = filterRequest.toString();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Sort sort = pageable.getSort();

        StringBuilder sortStringBuilder = new StringBuilder();
        sort.forEach(order ->
                sortStringBuilder.append(order.getProperty())
                        .append(":")
                        .append(order.getDirection())
                        .append(",")
        );

        if (sortStringBuilder.length() > 0) {
            sortStringBuilder.setLength(sortStringBuilder.length() - 1);
        }

        return String.format("get_all_products:%s:%d:%d:%s",
                filterRequestString, pageNumber, pageSize, sortStringBuilder.toString());
    }
    @Override
    public void clear() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
    }

    @Override
    public ProductPage getAllProducts(FilterRequest filterRequest, Pageable pageable) throws JsonProcessingException {
        String key = getKeyFrom(filterRequest, pageable);
        String json = (String) redisTemplate.opsForValue().get(key);
        return json != null ? redisObjectMapper.readValue(json, new TypeReference<>() {
        }) : null;
    }

    @Override
    public void saveAllProducts(ProductPage products, FilterRequest filterRequest, Pageable pageable) throws JsonProcessingException {
        String key = getKeyFrom(filterRequest, pageable);
        String json = redisObjectMapper.writeValueAsString(products);
        redisTemplate.opsForValue().set(key, json);
    }
}
