package com.example.demo.service.impl;

import com.example.demo.constant.ProductType;
import com.example.demo.dto.FilterRequest;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.exception.ProductCodeExistedException;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    private final ProductDetailRepository productDetailRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<Product> getAllProducts(FilterRequest filterDTO, PageRequest pageRequest) {
        return productRepository.findByFilter(filterDTO, pageRequest);
    }

    @Transactional
    @Override
    public Product insertProduct(ProductDTO productDTO) throws Exception {

        for (String type : productDTO.getTypes()) {
            if (!ProductType.getListProductTypes().contains(type))
                throw new DataNotFoundException("Type must be data, roaming, tra truoc, tra sau");
        }
        for (ProductDetail productDetail : productDTO.getProductDetails()) {
            if (isProductCodeExistsInProduct(productDetail.getProductCode(), productDTO.getOperator())) {
                throw new ProductCodeExistedException("Product code already exists");
            }
        }
        Product newProduct = productRepository.save(transferToProduct(productDTO, new Product()));
        List<ProductDetail> productDetails = productDTO.getProductDetails();
        for(ProductDetail productDetail : productDTO.getProductDetails()){
            productDetail.setProduct(newProduct);
        }
        productDetailRepository.saveAll(productDetails);
        return newProduct;
    }


    @Transactional
    @Override
    public void deleteProduct(Long productId) throws Exception {
        Product existProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find product with id: %d", productId)));
        if (existProduct.getStatus()) {
            existProduct.setStatus(false);
        }
        for(ProductDetail productDetail : existProduct.getProductDetails()){
            if(productDetail.getStatus()){
                productDetail.setStatus(false);
                productDetailRepository.save(productDetail);
            }
        }
        productRepository.save(existProduct);
    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find product with id: %d", productId)));
    }

    @Transactional
    @Override
    public Product updateProduct(ProductDTO productDTO, Long productId) throws Exception {
        for (String type : productDTO.getTypes()) {
            if (!ProductType.getListProductTypes().contains(type))
                throw new DataNotFoundException("Type must be data, roaming, tra truoc, tra sau");
        }
        Product existProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find product with id: %d", productId)));
        for(ProductDetail productDetail : productDTO.getProductDetails()){
            productDetail.setProduct(existProduct);
        }
        productDetailRepository.saveAll(productDTO.getProductDetails());
        return productRepository.save(transferToProduct(productDTO, existProduct));
    }

    private Boolean isProductCodeExistsInProduct(String productCode, String productOperator) {
        return productDetailRepository.existsByProductCodeAndProduct_Operator(productCode, productOperator);
    }

    private Product transferToProduct(ProductDTO productDTO, Product product){
        StringJoiner stringJoiner = new StringJoiner(",");
        for(String type : productDTO.getTypes()){
            stringJoiner.add(type);
        }

        modelMapper.map(productDTO, product);
        product.setTypes(stringJoiner.toString());
        return product;
    }

    private ProductDTO transferToProductDTO(Product product, ProductDTO productDTO){
        modelMapper.map(product, productDTO);
        List<String> types = List.of(product.getTypes().split(","));
        productDTO.setTypes(types);
        return productDTO;
    }
}
