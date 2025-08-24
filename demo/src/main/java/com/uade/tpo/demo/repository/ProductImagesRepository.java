package com.uade.tpo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.ProductImages;

import java.util.List;


@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {
    @Query("SELECT c FROM ProductImages c WHERE c.product.id = ?1 ")
    List<ProductImages> findProdcutById(Long productId);
}
