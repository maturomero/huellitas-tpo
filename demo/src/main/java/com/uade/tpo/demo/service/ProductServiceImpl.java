package com.uade.tpo.demo.service;

import java.util.ArrayList;
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
import com.uade.tpo.demo.exceptions.NoEntitiesFoundException;
import com.uade.tpo.demo.exceptions.ProductDuplicateException;
import com.uade.tpo.demo.exceptions.ProductNotExistException;
import com.uade.tpo.demo.exceptions.ProductRequiredFieldException;
import com.uade.tpo.demo.exceptions.ProductNotNegativeException;
import com.uade.tpo.demo.repository.AnimalRepository;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AnimalRepository animalRepository;
    
    public List<Product> getProducts() throws NoEntitiesFoundException{
        List<Product> p = productRepository.findAllStock();
        if(p.isEmpty()){
            throw new NoEntitiesFoundException();
        }
        return p;
    }
     
    public Optional<Product> getProductById(Long id) throws ProductNotExistException{
        Optional<Product> p =  productRepository.findByIdStock(id); 
        if(p.isEmpty()){
            throw new ProductNotExistException();
        }
        return p;
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

    public List<Product> getProductByName(String name) throws NoEntitiesFoundException{
        List<Product> p = productRepository.findAnyByExactName(name);
        if(p.isEmpty()){
            throw new NoEntitiesFoundException();
        }
        return p;
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

        if(!productRepository.findAnyByExactName(p.getName()).isEmpty()){
            throw new ProductDuplicateException();
        }

        List<Animal> animals = new ArrayList<>();

        if (p.getAnimalId() != null && !p.getAnimalId().isEmpty()) {
            List<Long> vistos = new ArrayList<>();
            int i = 0;
            while (i < p.getAnimalId().size()) {
                Long idAnimal = p.getAnimalId().get(i++);
                if (idAnimal == null) {
                    throw new ProductRequiredFieldException();
                }
                if(vistos.contains(idAnimal)){
                    continue;
                }
                Optional<Animal> a = animalRepository.findById(idAnimal);
                if(a.isEmpty()){
                    throw new AnimalNotExistException();
                }
                animals.add(a.get());
                vistos.add(idAnimal);
            }
        }else{
            throw new ProductRequiredFieldException();
        }


        Optional<Category> c = categoryRepository.findById(p.getCategoryId());
        if(c.isEmpty()){
            throw new CategoryNotExistException();
        }
        Double priceDiscount = p.getPrice() * 0.95;
        Product product = new Product(p.getName(), p.getPrice(),p.getStock());
        product.setAnimal(animals);
        product.setCategory(c.get());
        product.setPriceWithTransferDiscount(priceDiscount);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) throws ProductNotExistException{
        Optional<Product> p = productRepository.findById(id);
        if(p.isEmpty()){
            throw new ProductNotExistException();
        }
        p.get().setStatus(false);
        productRepository.save(p.get());
    }

    public void editProduct(Long id, ProductRequest pRequest) throws ProductNotExistException, CategoryNotExistException, AnimalNotExistException, ProductRequiredFieldException{
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            throw new ProductNotExistException();
        }
        
        Product p = product.get();

        if (pRequest.getName() != null && productRepository.findAnyByExactName(pRequest.getName()).isEmpty()) {
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
        }


        if(pRequest.getAnimalId() != null){ 
            List<Animal> animals = new ArrayList<>();

            if (!pRequest.getAnimalId().isEmpty()) {
                List<Long> vistos = new ArrayList<>();
                int i = 0;
                while (i < pRequest.getAnimalId().size()) {
                    Long idAnimal = pRequest.getAnimalId().get(i++);
                    if (idAnimal == null) {
                        throw new ProductRequiredFieldException();
                    }
                    if(vistos.contains(idAnimal)){
                        continue;
                    }
                    Optional<Animal> a = animalRepository.findById(idAnimal);
                    if(a.isEmpty()){
                        throw new AnimalNotExistException();
                    }
                    animals.add(a.get());
                    vistos.add(idAnimal);
                }
            }else{
                throw new ProductRequiredFieldException();
            }
            p.setAnimal(animals);
        }    
    
        productRepository.save(p);
    }

    public void reduceStock(Long id, int quantity) throws ProductNotExistException{
        Optional<Product> pOptional = productRepository.findById(id);
        if(pOptional.isEmpty()){
            throw new ProductNotExistException();
        }

        Product p = pOptional.get();
        p.setStock(p.getStock() - quantity);
        productRepository.save(p);
    }
}
