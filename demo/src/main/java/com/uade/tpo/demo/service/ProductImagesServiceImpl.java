package com.uade.tpo.demo.service;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ProductImages;
import com.uade.tpo.demo.entity.dto.AddFileRequest;
import com.uade.tpo.demo.entity.dto.ProductImageUpload;
import com.uade.tpo.demo.entity.dto.ProductImagesResponse2;
import com.uade.tpo.demo.exceptions.ProductImagesNotExistException;
import com.uade.tpo.demo.repository.ProductImagesRepository;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class ProductImagesServiceImpl implements ProductImagesService {

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductImages uploadImage(Long productId, Blob blobImage){
        ProductImages pI = new ProductImages();
        Optional<Product> p = productRepository.findById(productId);
        pI.setUrlImage(blobImage);
        pI.setProduct(p.get());
        
        return productImagesRepository.save(pI);
        
    }


    public ProductImages viewById(long id) throws ProductImagesNotExistException{
        Optional<ProductImages> pI = productImagesRepository.findById(id);
        if(pI.isEmpty()){
            throw new ProductImagesNotExistException();
        }
        return pI.get();
    }
    
    public List<Long> getProductImgaesById(Long productId) throws ProductImagesNotExistException{
        List<Long> pI = productImagesRepository.findProductById(productId);
        if(pI.isEmpty()){
            throw new ProductImagesNotExistException();
        }
        return pI;
    }

    public void deleteOneImageProduct(Long imageId) throws ProductImagesNotExistException{
        Optional<ProductImages> pI = productImagesRepository.findById(imageId);
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
