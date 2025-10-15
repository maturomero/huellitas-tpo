package com.uade.tpo.demo.service;

import java.sql.Blob;
import java.util.List;

import com.uade.tpo.demo.entity.ProductImages;
import com.uade.tpo.demo.exceptions.ProductImagesNotExistException;
import com.uade.tpo.demo.exceptions.ProductNotExistException;

public interface ProductImagesService {
    public ProductImages uploadImage(Long productId, Blob blobImage) throws ProductNotExistException;
    public List<Long> getProductImgaesById(Long productId) throws ProductImagesNotExistException;
    public ProductImages viewById(long id) throws ProductImagesNotExistException;
    public void deleteOneImageProduct(Long imageId) throws ProductImagesNotExistException;
    public void deleteAllImagesProduct(Long productId) throws ProductImagesNotExistException;
}
