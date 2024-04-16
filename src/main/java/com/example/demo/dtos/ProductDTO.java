package com.example.demo.dtos;

import com.example.demo.models.ProductDetail;
import com.example.demo.models.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDTO {
    private String name;
    private String operator;
    private List<Type> types;
    @JsonProperty("product_details")
    private List<ProductDetail> productDetails;
}
