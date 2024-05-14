package com.example.demo.controller;

import com.example.demo.common.constant.ResponseConstant;
import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.request.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.dto.response.Response;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.common.constant.ResponseConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;


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



    @GetMapping("/get-product-by-id")
    public ResponseEntity<Response<Product>> getProductById(@RequestParam Long id){
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
                    new Response<>(CREATE_CODE, "insert product successfully",product)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }
    @PutMapping("/update-product")
    public ResponseEntity<Response<Product>> updateProduct(@RequestParam Long id, @RequestBody ProductDTO productDTO){
        try {
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, "Update Product Successfully",productService.updateProduct(productDTO, id))
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }
    @DeleteMapping("/delete-product")
    public ResponseEntity<Response<Product>> deleteProduct(@RequestParam Long id){
        Product product = new Product();
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE,"Delete Product Successfully", product)
            );
        } catch (Exception e) {
            return ResponseEntity.ok().body(
                    new Response<>(ERROR_CODE,e.getMessage())
            );
        }
    }
}
