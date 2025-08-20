package com.uade.tpo.demo.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uade.tpo.demo.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    
    // buscamos valores exacto sin mirar mayusculas ni minusculas
    Optional<Category> findByDescriptionIgnoreCase(String description);
    // buscamos si EXISTE valores exactos, sin respetar lo que viene siendo mayusculas ni minusculas (para mirar repetidos y demas)
    boolean existsByDescriptionIgnoreCase(String description);
    //buscamos un desripcion parcial (ALI) --> Alimentos o cosas similares. que esten dentro de esta busqueda parcial.
    List<Category> findByDescriptionContainingIgnoreCase(String q);

}
