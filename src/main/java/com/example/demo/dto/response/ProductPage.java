package com.example.demo.dto.response;

import com.example.demo.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductPage {
    private List<Product> products;
    private int page;
    private int size;
    private long totalPages;
    private long totalElements;

}
