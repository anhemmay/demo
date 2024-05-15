package com.example.demo.controller;

import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.request.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.dto.response.Response;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.common.constant.ResponseCode.*;
import static com.example.demo.common.constant.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;

    private final Logger logger= LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/filter")
    public ResponseEntity<Response<Page<Product>>> getAllProducts(@RequestBody FilterRequest filterRequest,
                                                                  Pageable pageable){
        try{

            Page<Product> productPage = productService.getAllProducts(filterRequest, pageable);
            return ResponseEntity.ok().body(new Response<>(SUCCESS_CODE,"filter",productPage));
        }catch(Exception ex){

            return ResponseEntity.badRequest().body(new Response<>(ERROR_CODE, ex.getMessage()));
        }

    }

//    @Cacheable(value = "products", key = "#id")
    @GetMapping("/get-product-by-id")
    public ResponseEntity<Response<Product>> getProductById(@RequestParam Long id){
        logger.error("hello thang");
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
