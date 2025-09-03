package com.uade.tpo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Product;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT c FROM Product c WHERE c.name = ?1 and c.status = true and c.stock > 0")
    List<Product> findByExactName(String name);

    @Query("SELECT c FROM Product c WHERE c.name = ?1")
    List<Product> findAnyByExactName(String name);

    //Traer todo lo que contenga el nombre
    @Query("SELECT c FROM Product c WHERE c.name LIKE %?1% and c.status = true and c.stock > 0")  
    List<Product> findByName(String name);

    @Query("SELECT c FROM Product c WHERE c.price >= ?1 AND c.price <= ?2 and c.status = true and c.stock > 0")
    List<Product> findByPrice(double priceMin, double priceMax);

    @Query("SELECT c FROM Product c WHERE c.status = true")
    List<Product> availableProducts();

    @Query("SELECT DISTINCT c FROM Product c JOIN c.animal a WHERE a.id = ?1 and c.status = true and c.stock > 0")
    List<Product> findByAnimalId(Long id);

    @Query("SELECT c FROM Product c WHERE c.category.id = ?1 and c.status = true and c.stock > 0")
    List<Product> findByCategoryId(Long id);  
    
    @Query("SELECT c FROM Product c WHERE c.status = true and c.stock > 0")
    List<Product> findAllStock();

    @Query("SELECT c FROM Product c WHERE c.id = ?1 and c.status = true and c.stock > 0")
    Optional<Product> findByIdStock(Long id);

}
