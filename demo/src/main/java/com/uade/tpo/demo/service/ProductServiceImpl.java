package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.ProductNotExistException;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ProductRepository;

public class ProductServiceImpl {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id); 
    }

    public List<Product> getProductByCategoryId(Long id){
        return productRepository.findByCategoryId(id);
    }

    public Product createProduct()
}
