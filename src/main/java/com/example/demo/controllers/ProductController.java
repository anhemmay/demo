package com.example.demo.controllers;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.models.Product;
import com.example.demo.responses.ProductListResponse;
import com.example.demo.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ProductListResponse> getAllProducts(
            @RequestParam(defaultValue = "1", name = "status", required = false) Boolean status,
            @RequestParam(name = "product_code", required = false) String productCode,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "cycle", required = false) List<String> cycle,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "min_price", required = false) Float minPrice,
            @RequestParam(name = "max_price", required = false) Float maxPrice,
            @RequestParam(defaultValue = "0", name = "page", required = true) int page,
            @RequestParam(defaultValue = "1", name = "limit", required = true) int limit){
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("id")
        );

        Page<Product> productPage = productService.getAllProduct(status, productCode, name, cycle, type, minPrice, maxPrice, pageRequest);
        List<Product> products = productPage.getContent();
        ProductListResponse productListResponse = new ProductListResponse();
        productListResponse.setProductResponses(products);
        productListResponse.setTotalPages(productPage.getTotalPages());
        return ResponseEntity.ok().body(productListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(productService.getProductById(id));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertProduct(@RequestBody ProductDTO productDTO){
        try {
            return ResponseEntity.ok().body(productService.insertProduct(productDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        try {
            return ResponseEntity.ok().body(productService.updateProduct(productDTO, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body("Delete Product successfully");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
