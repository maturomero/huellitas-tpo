package com.uade.tpo.demo.service;

import java.util.List;

import com.uade.tpo.demo.entity.ProductImages;
import com.uade.tpo.demo.entity.dto.ProductImageUpload;
import com.uade.tpo.demo.exceptions.ProductImagesNotExistException;

public interface ProductImagesService {
    public ProductImages uploadImage(Long productId, ProductImageUpload productImageUpload );
    public List<ProductImages> getProductImgaesById(Long productId);
    public void deleteOneImageProduct(Long imageId) throws ProductImagesNotExistException;
    public void deleteAllImagesProduct(Long productId) throws ProductImagesNotExistException;
}
