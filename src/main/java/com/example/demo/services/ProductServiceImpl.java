package com.example.demo.services;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{
    private final ProductRepository productRepository;

    @Override
    public Page<Product> getAllProduct(Boolean status, String productCode, String name, List<Integer> cycle, String type, Pageable pageable) {
        return productRepository.findAllProduct(status, productCode, name, cycle, type, pageable);
    }


//    @Override
//    public Product insertProduct(ProductDTO productDTO) {
//        Product newProduct = Product
//                .builder()
//                .name(productDTO.getName())
//                .type(productDTO.getType())
//                .build();
//        return productRepository.save(newProduct);
//    }
//
//    @Override
//    public void deleteProduct(Long productId) {
//        productRepository.deleteById(productId);
//    }
//
//    @Override
//    public Product getProductById(Long productId) throws Exception {
//        return productRepository.findById(productId)
//                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id"));
//    }
//
//    @Override
//    public Product updateProduct(ProductDTO productDTO, Long productId) throws Exception {
//        Product existProduct = getProductById(productId);
//        existProduct.setName(productDTO.getName());
//        existProduct.setType(productDTO.getType());
//        return productRepository.save(existProduct);
//    }
}
