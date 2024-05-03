package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterRequest {
    @JsonProperty("status")
    private Boolean status;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cycles")
    private List<Integer> cycles;
    @JsonProperty("types")
    private String types;
    @JsonProperty("min_price")
    private Float minPrice;
    @JsonProperty("max_price")
    private Float maxPrice;
    @JsonProperty("page")
    private int page;
    @JsonProperty("limit")
    private int limit;
}
