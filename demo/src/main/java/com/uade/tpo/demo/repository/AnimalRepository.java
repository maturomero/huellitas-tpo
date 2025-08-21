package com.uade.tpo.demo.repository;

import java.util.List;
import com.uade.tpo.demo.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{
    @Query("SELECT a FROM Animal a WHERE a.name = ?1")
    List<Animal>findByName(String name);
}
