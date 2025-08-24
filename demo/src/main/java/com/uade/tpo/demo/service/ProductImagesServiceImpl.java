package com.uade.tpo.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ProductImages;
import com.uade.tpo.demo.entity.dto.ProductImageUpload;
import com.uade.tpo.demo.repository.ProductImagesRepository;
import com.uade.tpo.demo.repository.ProductRepository;

public class ProductImagesServiceImpl {

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductImages uploadImage(Long productId, ProductImageUpload productImageUpload ){
        ProductImages pI = new ProductImages();
        Optional<Product> p = productRepository.findById(productId);
        pI.setProduct(p.get());
        pI.setUrlImage(productImageUpload.getUrlImgae());
        
        return productImagesRepository.save(ProductImages);
        
    }
    
}
