package com.example.demo.services;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.models.Product;
import com.example.demo.models.ProductDetail;
import com.example.demo.models.Type;
import com.example.demo.repositories.ProductDetailRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{
    private final ProductRepository productRepository;

    private final ProductDetailRepository productDetailRepository;

    private final TypeRepository typeRepository;
    @Override
    public Page<Product> getAllProduct(Boolean status, String productCode, String name, List<String> cycle, String type, Float minPrice, Float maxPrice, Pageable pageable) {
        return productRepository.findAllProduct(
                status,
                productCode,
                name,
                cycle,
                type,
                minPrice,
                maxPrice,
                pageable
        );
    }

    @Transactional
    @Override
    public Product insertProduct(ProductDTO productDTO) throws Exception {
        for(Type type: productDTO.getTypes()){
            if(!type.getName().equals(Type.DATA) &&
                !type.getName().equals(Type.ROAMING) &&
                !type.getName().equals(Type.TRA_SAU) &&
                !type.getName().equals(Type.TRA_TRUOC)
            ){
                throw new DataNotFoundException("product type should be data, roaming, tra truoc, tra sau");
            }
        }
        try {
            Product newProduct = productRepository.save(Product
                    .builder()
                    .name(productDTO.getName())
                    .operator(productDTO.getOperator())
                    .type(productDTO.getTypes())
                    .status(true)
                    .productDetails(productDTO.getProductDetails())
                    .build()
            );
            if(productDTO.getTypes() != null){
                for (Type type : productDTO.getTypes()) {
                    type.setProduct(newProduct);
                    typeRepository.save(type);
                }
            }
            if(productDTO.getProductDetails() != null){
                for (ProductDetail productDetail : productDTO.getProductDetails()) {
                    productDetail.setProduct(newProduct);
                    productDetail.setStatus(true);
                    productDetailRepository.save(productDetail);
                }
            }
            return newProduct;
        }
        catch (Exception e){
            throw new Exception("some error");
        }
    }


    @Transactional
    @Override
    public void deleteProduct(Long productId) throws Exception {
        Product existProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find product with id: %d", productId)));
        productRepository.deleteById(productId);
    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find product with id: %d", productId)));
    }

    @Transactional
    @Override
    public Product updateProduct(ProductDTO productDTO, Long productId) throws Exception {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find product with id: %d", productId)));
        existingProduct.setName(productDTO.getName());
        existingProduct.setOperator(productDTO.getOperator());
        existingProduct.setType(productDTO.getTypes());

        for(ProductDetail productDetail: productDTO.getProductDetails()){
            productDetail.setProduct(existingProduct);
            productDetailRepository.save(productDetail);
        }
        for(Type type: productDTO.getTypes()){
            type.setProduct(existingProduct);
            typeRepository.save(type);
        }
        return productRepository.save(existingProduct);
    }
}
