package com.uade.tpo.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.ProductImages;
import com.uade.tpo.demo.entity.dto.ProductImageUpload;
import com.uade.tpo.demo.exceptions.ProductImagesNotExistException;
import com.uade.tpo.demo.service.ProductImagesService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/products/images")
public class ProductImageController {
    @Autowired
    private ProductImagesService productImagesService;

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductImages>> getProductImages(@PathVariable Long productId) throws ProductImagesNotExistException {
        List<ProductImages> i = productImagesService.getProductImgaesById(productId);
        return ResponseEntity.ok(i);
    }
    
    @PostMapping("/{productId}")
    public ResponseEntity<ProductImages> uploadImage(@PathVariable Long productId , @RequestBody ProductImageUpload productImageUpload) {
        ProductImages i = productImagesService.uploadImage(productId, productImageUpload);
        return ResponseEntity.ok(i);
    }
    
    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteOneImageProduct(@PathVariable Long idImage) throws ProductImagesNotExistException{
        productImagesService.deleteOneImageProduct(idImage);
        return ResponseEntity.ok("La imagen del producto se eliminó correctamente. ");
    }

    @DeleteMapping("/{productId}/all")
    public ResponseEntity<String> deleteAllImagesProduct(@PathVariable Long idProduct ) throws ProductImagesNotExistException{
        productImagesService.getProductImgaesById(idProduct);
        return ResponseEntity.ok("Todas las imagenes del producto se eliminaron correctamente. ");
    }
}
