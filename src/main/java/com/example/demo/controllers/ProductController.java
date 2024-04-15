package com.example.demo.controllers;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.models.Product;
import com.example.demo.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(required = true, defaultValue = "0") int page,
                                                        @RequestParam(required = true, defaultValue = "1") int limit){
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("id")
        );
        Page<Product> productPage = productService.getAllProduct(pageRequest);
        return ResponseEntity.ok().body(productPage);
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
    public ResponseEntity<Product> insertProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok().body(productService.insertProduct(productDTO));
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
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Delete Product successfully");
    }
}
