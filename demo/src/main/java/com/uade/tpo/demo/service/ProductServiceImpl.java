package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Animal;
import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.dto.ProductRequest;
import com.uade.tpo.demo.exceptions.AnimalNotExistException;
import com.uade.tpo.demo.exceptions.CategoryNotExistException;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.exceptions.ProductNotExistException;
import com.uade.tpo.demo.exceptions.ProductRequiredFieldException;
import com.uade.tpo.demo.exceptions.ProductNotNegativeException;
import com.uade.tpo.demo.repository.AnimalRepository;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AnimalRepository animalRepository;
    
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id); 
    }

    public List<Product> getProductsByAnimalId(Long id) throws AnimalNotExistException{
        Optional<Animal> a = animalRepository.findById(id);
        if(a.isEmpty()){
            throw new AnimalNotExistException();
        }
        return productRepository.findByAnimalId(id);
    }

    public List<Product> getProductByCategoryId(Long id) throws CategoryNotExistException{
        Optional<Category> c = categoryRepository.findById(id);
        if(c.isEmpty()){
            throw new CategoryNotExistException();
        }
        return productRepository.findByCategoryId(id);
    }

    public List<Product> getProductByName(String name){
        return productRepository.findByExactName(name);
    }

    public List<Product> getProductsByPrice(double priceMin, double priceMax){
        return productRepository.findByPrice(priceMin, priceMax);
    }

    public Product createProduct(ProductRequest p ) throws ProductDuplicateException, AnimalNotExistException, ProductRequiredFieldException, ProductNotNegativeException, CategoryNotExistException{
       
        
        if (p.getName() == null || p.getName().isEmpty()) {
            throw new ProductRequiredFieldException();
        }
        if (p.getPrice() == null) {
            throw new ProductRequiredFieldException();
        }
        if (p.getStock() == null) {
            throw new ProductRequiredFieldException();
        }
        if (p.getCategoryId() == null) {
            throw new ProductRequiredFieldException();
        }
        if (p.getPrice() < 0) {
            throw new ProductNotNegativeException();
        }
        if (p.getStock().intValue() < 0) {
            throw new ProductNotNegativeException();
        }

        if(!productRepository.findByExactName(p.getName()).isEmpty()){
            throw new ProductDuplicateException();
        }

        Animal animal = null;
        if (p.getAnimalId() != null) {
            Optional<Animal> a = animalRepository.findById(p.getAnimalId());
            if (a.isEmpty()){
                throw new AnimalNotExistException();
            }
            animal = a.get();
        } 
        
        Optional<Category> c = categoryRepository.findById(p.getCategoryId());
        if(c.isEmpty()){
            throw new CategoryNotExistException();
        }
        Product product = new Product(p.getName(), p.getPrice(),p.getStock());
        product.setAnimal(animal);
        product.setCategory(c.get());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) throws ProductNotExistException{
        Optional<Product> p = productRepository.findById(id);
        if(p.isEmpty()){
            throw new ProductNotExistException();
        }
        productRepository.deleteById(id);
    }

    public void editProduct(Long id, ProductRequest pRequest) throws ProductNotExistException, CategoryNotExistException, AnimalNotExistException, ProductRequiredFieldException{
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            throw new ProductNotExistException();
        }
        
        Product p = product.get();

        if (pRequest.getName() != null && productRepository.findByExactName(pRequest.getName()).isEmpty()) {
            p.setName(pRequest.getName());
        }
        if (pRequest.getPrice() != null) {
            p.setPrice(pRequest.getPrice());
        }
        if (pRequest.getStock() != null) {
            p.setStock(pRequest.getStock());
        }

        if (pRequest.getCategoryId() != null) {
            Optional<Category> c = categoryRepository.findById(pRequest.getCategoryId());
            if(c.isEmpty()){
                throw new CategoryNotExistException();}
            else{
                 p.setCategory(c.get());
            }

        }else{
            throw new ProductRequiredFieldException();
        }


        if (pRequest.getAnimalId() != null) {
            Optional<Animal> a = animalRepository.findById(pRequest.getAnimalId());
            if (a.isPresent()) {
                p.setAnimal(a.get());
            } else {
            throw new AnimalNotExistException();
            }
        } else {
            p.setAnimal(null);
        }
    
        productRepository.save(p);
    }
}
