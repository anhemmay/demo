package com.example.demo.controller;

import com.example.demo.dto.FilterRequest;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.response.Response;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<Response<Page<Product>>> getAllProducts(@RequestBody FilterRequest filterRequest){
        PageRequest request = PageRequest.of(
                filterRequest.getPage(),
                filterRequest.getLimit(),
                Sort.by("id").ascending()
        );
        Page<Product> productPage = productService.getAllProducts(filterRequest, request);
        return ResponseEntity.ok().body(new Response<>("200","filter",productPage));
    }



    @GetMapping("/{id}")
    public ResponseEntity<Response<Product>> getProductById(@PathVariable Long id){
        Product product = new Product();
        try{
            product = productService.getProductById(id);
            return ResponseEntity.ok().body(
                    new Response<>("200","get product by id", product)
            );

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>("400",e.getMessage())
            );
        }
    }

    @PostMapping
    public ResponseEntity<Response<Product>> insertProduct(@RequestBody ProductDTO productDTO){
        Product product = new Product();
        try {
            product = productService.insertProduct(productDTO);
            return ResponseEntity.ok().body(
                    new Response<>("200", "insert product successfully",product)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>("400", e.getMessage())
            );
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response<Product>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        try {
            return ResponseEntity.ok().body(
                    new Response<>("200", "Update Product Successfully",productService.updateProduct(productDTO, id))
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>("400", e.getMessage())
            );
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Product>> deleteProduct(@PathVariable Long id){
        Product product = new Product();
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body(
                    new Response<>("200","Delete Product Successfully", product)
            );
        } catch (Exception e) {
            return ResponseEntity.ok().body(
                    new Response<>("400",e.getMessage())
            );
        }
    }
}
