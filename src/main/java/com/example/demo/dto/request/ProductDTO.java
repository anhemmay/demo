package com.example.demo.dto.request;

import com.example.demo.model.ProductDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String name;
    private String operator;
    private String types;
    private Boolean status;
    @JsonProperty("product_details")
    private List<ProductDetail> productDetails;

}
