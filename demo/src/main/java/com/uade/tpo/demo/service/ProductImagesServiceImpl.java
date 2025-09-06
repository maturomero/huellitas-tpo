package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ProductImages;
import com.uade.tpo.demo.entity.dto.ProductImageUpload;
import com.uade.tpo.demo.exceptions.ProductImagesNotExistException;
import com.uade.tpo.demo.repository.ProductImagesRepository;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class ProductImagesServiceImpl implements ProductImagesService {

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductImages uploadImage(Long productId, ProductImageUpload productImageUpload ){
        ProductImages pI = new ProductImages();
        Optional<Product> p = productRepository.findById(productId);
        pI.setUrlImage(productImageUpload.getUrlImage());
        pI.setProduct(p.get());
        
        return productImagesRepository.save(pI);
        
    }
    
    public List<ProductImages> getProductImgaesById(Long productId) throws ProductImagesNotExistException{
        List<ProductImages> pI = productImagesRepository.findProdcutById(productId);
        if(pI.isEmpty()){
            throw new ProductImagesNotExistException();
        }
        return pI;
    }

    public void deleteOneImageProduct(Long imageId) throws ProductImagesNotExistException{
        List<ProductImages> pI = productImagesRepository.findProdcutById(imageId);
        if(pI.isEmpty()){
            throw new ProductImagesNotExistException();
        }
        productImagesRepository.deleteById(imageId);
    }

    public void deleteAllImagesProduct(Long productId) throws ProductImagesNotExistException{
    List<ProductImages> pI = productImagesRepository.findProdcutById(productId);
    if(pI.isEmpty()){
        throw new ProductImagesNotExistException();
    }
    productImagesRepository.deleteAll(pI);
    }

}
