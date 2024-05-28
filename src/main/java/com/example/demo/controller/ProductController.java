package com.example.demo.controller;

import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.request.ProductDTO;
import com.example.demo.dto.response.ProductPage;
import com.example.demo.dto.response.Response;
import com.example.demo.model.Product;
import com.example.demo.service.IProductRedisService;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.common.constant.ResponseCode.*;
import static com.example.demo.common.constant.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {
    private final IProductService productService;

    private final IProductRedisService productRedisService;

    @PostMapping("/filter")
    public ResponseEntity<Response<ProductPage>> getAllProducts(@RequestBody FilterRequest filterRequest,
                                                                Pageable pageable){
        try{
            ProductPage productPageInRedis = productRedisService.getAllProducts(filterRequest, pageable);
            if(productPageInRedis == null){
                Page<Product> products = productService.getAllProducts(filterRequest, pageable);
                ProductPage productPage = ProductPage.builder()
                        .page(pageable.getPageNumber())
                        .size(pageable.getPageSize())
                        .totalPages(products.getTotalPages())
                        .totalElements(products.getTotalElements())
                        .products(products.getContent())
                        .build();
                productRedisService.saveAllProducts(productPage, filterRequest, pageable);

                return ResponseEntity.ok().body(new Response<>(SUCCESS_CODE,"filter",productPage));
            }
            else {
                return ResponseEntity.ok().body(new Response<>(SUCCESS_CODE,"filter",productPageInRedis));
            }
        }catch(Exception ex){

            return ResponseEntity.badRequest().body(new Response<>(ERROR_CODE, ex.getMessage()));
        }

    }

//    @Cacheable(value = "products", key = "#id")
    @GetMapping("/get-product-by-id")
    public ResponseEntity<Response<Product>> getProductById(@RequestParam Long id){
        log.error("hello controller");
        Product product = new Product();
        try{
            product = productService.getProductById(id);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE,"get product by id", product)
            );

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE,e.getMessage())
            );
        }
    }

    @PostMapping("/insert-product")
    public ResponseEntity<Response<Product>> insertProduct(@RequestBody ProductDTO productDTO){
        Product product = new Product();
        try {
            product = productService.insertProduct(productDTO);
            return ResponseEntity.ok().body(
                    new Response<>(CREATE_CODE, CREATE_PRODUCT_SUCCESSFULLY,product)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }
//    @CachePut(value = "products", key = "#id")
    @PutMapping("/update-product")
    public ResponseEntity<Response<Product>> updateProduct(@RequestParam Long id, @RequestBody ProductDTO productDTO){
        try {
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, UPDATE_PRODUCT_SUCCESSFULLY,productService.updateProduct(productDTO, id))
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }
//    @CacheEvict(value = "products", key = "#id")
    @DeleteMapping("/delete-product")
    public ResponseEntity<Response<Product>> deleteProduct(@RequestParam Long id){
        Product product = new Product();
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE,DELETE_PRODUCT_SUCCESSFULLY, product)
            );
        } catch (Exception e) {
            return ResponseEntity.ok().body(
                    new Response<>(ERROR_CODE,e.getMessage())
            );
        }
    }
}
