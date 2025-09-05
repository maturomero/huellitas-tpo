package com.uade.tpo.demo.controllers;

import java.util.List;
import java.util.Optional;
import com.uade.tpo.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.AnimalNotExistException;
import com.uade.tpo.demo.exceptions.CategoryNotExistException;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.exceptions.ProductNotExistException;
import com.uade.tpo.demo.exceptions.ProductNotNegativeException;
import com.uade.tpo.demo.exceptions.ProductRequiredFieldException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> p = productService.getProductById(id);
        if(p.isPresent()){
            return ResponseEntity.ok(p.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/productByPrice")
    public ResponseEntity<List<Product>> getProductsByPrice(@RequestParam Double priceMin, @RequestParam Double priceMax) {
        return ResponseEntity.ok(productService.getProductsByPrice(priceMin, priceMax));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) throws CategoryNotExistException {
        return ResponseEntity.ok(productService.getProductByCategoryId(id));
    }

    @GetMapping("/animals/{id}")
    public ResponseEntity<List<Product>> getProductsByAnimal(@PathVariable Long id) throws AnimalNotExistException {
        return ResponseEntity.ok(productService.getProductsByAnimalId(id));
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest) throws ProductNotNegativeException, ProductDuplicateException, AnimalNotExistException, ProductRequiredFieldException, CategoryNotExistException {
        if(productRequest.getPrice() <= 0){
            throw new ProductNotNegativeException();
        }
        
        if(productRequest.getStock() < 0){
            throw new ProductNotNegativeException();
        }

        Product p = productService.createProduct(productRequest);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ProductNotExistException{
        productService.deleteProduct(id);
        return ResponseEntity.ok("Se elimino el producto. ");
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<String> editProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) throws ProductNotNegativeException, ProductNotExistException, CategoryNotExistException, AnimalNotExistException, ProductRequiredFieldException {
        if(productRequest.getPrice() != null && productRequest.getPrice() <= 0){
            throw new ProductNotNegativeException();
        }
        if(productRequest.getStock() != null && productRequest.getStock() < 0){
            throw new ProductNotNegativeException();
        }

        productService.editProduct(id, productRequest);
        return ResponseEntity.ok("Se edito el producto. ");
    }

}
