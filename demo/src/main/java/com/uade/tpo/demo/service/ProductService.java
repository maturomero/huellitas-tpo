package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.AnimalNotExistException;
import com.uade.tpo.demo.exceptions.CategoryNotExistException;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.exceptions.ProductNotExistException;
import com.uade.tpo.demo.exceptions.ProductNotNegativeException;
import com.uade.tpo.demo.exceptions.ProductRequiredFieldException;

public interface ProductService {
    public List<Product> getProducts();
    public Optional<Product> getProductById(Long id);
    public List<Product> getProductsByAnimalId(Long id) throws AnimalNotExistException;
    public List<Product> getProductByCategoryId(Long id) throws CategoryNotExistException;
    public List<Product> getProductByName(String name);
    public List<Product> getProductsByPrice(double priceMin, double priceMax);
    public Product createProduct(ProductRequest p ) throws ProductDuplicateException, AnimalNotExistException, ProductRequiredFieldException, ProductNotNegativeException, CategoryNotExistException;
    public void deleteProduct(Long id) throws ProductNotExistException;
    public void editProduct(Long id, ProductRequest pRequest) throws ProductNotExistException, CategoryNotExistException, AnimalNotExistException, ProductRequiredFieldException;
    public void reduceStock(Long id, int quantity) throws ProductNotExistException;

}
