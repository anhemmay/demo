package com.example.demo.responses;

import com.example.demo.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {
    @JsonProperty("products")
    List<Product> productResponses;
    @JsonProperty("total_pages")
    Integer totalPages;
}
