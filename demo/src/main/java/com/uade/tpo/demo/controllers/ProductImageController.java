package com.uade.tpo.demo.controllers;

import java.util.Base64;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.ProductImages;
import com.uade.tpo.demo.entity.dto.AddFileRequest;
import com.uade.tpo.demo.entity.dto.ProductImageResponse;
import com.uade.tpo.demo.entity.dto.ProductImageUpload;
import com.uade.tpo.demo.entity.dto.ProductImagesResponse2;
import com.uade.tpo.demo.exceptions.ProductImagesNotExistException;
import com.uade.tpo.demo.service.ProductImagesService;

import java.io.IOException;
import javax.sql.rowset.serial.SerialException;

import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/products/images")
public class ProductImageController {
    @Autowired
    private ProductImagesService productImagesService;

    @GetMapping("/{productId}")
    public ResponseEntity<List<Long>> getProductImages(@PathVariable Long productId) throws ProductImagesNotExistException {
        List<Long> i = productImagesService.getProductImgaesById(productId);
        return ResponseEntity.ok(i);
    }
    
    @PostMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductImages> uploadImage(@PathVariable Long productId , @ModelAttribute AddFileRequest addFileRequest) throws IOException, SerialException, SQLException{
        byte[] bytes = addFileRequest.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        ProductImages i = productImagesService.uploadImage(productId, blob);
        return ResponseEntity.ok(i);
    }

    @GetMapping()
    public ResponseEntity<ProductImageResponse> displayImage(@RequestParam("id") long id) throws IOException, SQLException, ProductImagesNotExistException {
        ProductImages image = productImagesService.viewById(id);
        String encodedString = Base64.getEncoder()
                .encodeToString(image.getUrlImage().getBytes(1, (int) image.getUrlImage().length()));
        return ResponseEntity.ok().body(ProductImageResponse.builder().file(encodedString).id(id).build());
    }
    
    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteOneImageProduct(@PathVariable Long imageId) throws ProductImagesNotExistException{
        productImagesService.deleteOneImageProduct(imageId);
        return ResponseEntity.ok("La imagen del producto se eliminó correctamente. ");
    }

    @DeleteMapping("/{productId}/all")
    public ResponseEntity<String> deleteAllImagesProduct(@PathVariable Long productId ) throws ProductImagesNotExistException{
        productImagesService.deleteAllImagesProduct(productId);
        return ResponseEntity.ok("Todas las imagenes del producto se eliminaron correctamente. ");
    }
}
