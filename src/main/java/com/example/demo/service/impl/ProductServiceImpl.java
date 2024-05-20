package com.example.demo.service.impl;

import com.example.demo.common.constant.ProductType;
import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.request.ProductDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.exception.ProductCodeExistedException;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.IProductService;
import com.example.demo.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.demo.common.constant.ResponseMessage.PRODUCT_CODE_EXISTS;
import static com.example.demo.common.constant.ResponseMessage.WRONG_TYPE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    private final ProductDetailRepository productDetailRepository;

    private final ModelMapper modelMapper;


    @Override
    public Page<Product> getAllProducts(FilterRequest filterRequest, Pageable pageable) {
        return productRepository.findByFilter(filterRequest, pageable);
    }

    @Transactional
    @CachePut(value = "products", key = "#result.id")
    @Override
    public Product insertProduct(ProductDTO productDTO) throws Exception {
        if(!isProductType(productDTO)){
            throw new DataNotFoundException(WRONG_TYPE);
        }
        productDTO.setTypes("," + productDTO.getTypes() + ",");
        for (ProductDetail productDetail : productDTO.getProductDetails()) {
            if (isProductCodeExistsInProduct(productDetail.getProductCode(), productDTO.getOperator())) {
                throw new ProductCodeExistedException(PRODUCT_CODE_EXISTS);
            }
        }
        Product newProduct = ConvertUtil.convertObject(productDTO, object -> modelMapper.map(object, Product.class));
        List<ProductDetail> productDetails = productDTO.getProductDetails();
        for(ProductDetail productDetail : productDTO.getProductDetails()){
            productDetail.setProduct(newProduct);
            productDetailRepository.save(productDetail);
        }
        return productRepository.save(newProduct);
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
        log.error("log service");
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find product with id: %d", productId)));
    }

    @Transactional
    @Override
    public Product updateProduct(ProductDTO productDTO, Long productId) throws Exception {
        if(!isProductType(productDTO)){
            throw new DataNotFoundException(WRONG_TYPE);
        }
        productDTO.setTypes("," + productDTO.getTypes() + ",");
        Product existProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find product with id: %d", productId)));
        for(ProductDetail productDetail : productDTO.getProductDetails()){
            boolean checkProductCode = isProductCodeExistsInProduct(productDetail.getProductCode(), productDTO.getOperator());
            if(checkProductCode){
                if(productDetail.getId() == null){
                    throw new ProductCodeExistedException(PRODUCT_CODE_EXISTS );
                }
                else{
                    Optional<ProductDetail> existProductDetail = productDetailRepository.findById(productDetail.getId());
                    if(existProductDetail.isEmpty()){
                        throw new DataNotFoundException(String.format("Cannot find product detail with id: %d", productDetail.getId()));
                    }
                    else if(!existProductDetail.get().getId().equals(productDetail.getId())){
                        throw new DataNotFoundException(PRODUCT_CODE_EXISTS);
                    }
                }
            }

            productDetail.setProduct(existProduct);
            productDetailRepository.save(productDetail);
        }
        ConvertUtil.convertObject(productDTO, object -> {
            modelMapper.map(object, existProduct);
            return existProduct;
        });
        return productRepository.save(existProduct);
    }

    private Boolean isProductCodeExistsInProduct(String productCode, String productOperator) {
        return productDetailRepository.existsByProductCodeAndProduct_Operator(productCode, productOperator);
    }


    private Boolean isProductType(ProductDTO productDTO){
        List<String> typesList = List.of(productDTO.getTypes().split(","));
        for (String type : typesList) {
            if (!ProductType.getListProductTypes().contains(type)){
                return false;
            }
        }
        return true;
    }

}
