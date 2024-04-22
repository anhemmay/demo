package com.example.demo.response;

import com.example.demo.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("current_page")
    private Integer currentPage;
    @JsonProperty("products_per_page")
    private Integer productsPerPage;
    @JsonProperty("total_products")
    private Integer totalProducts;
    @JsonProperty("products")
    private List<Product> productResponses;

}
