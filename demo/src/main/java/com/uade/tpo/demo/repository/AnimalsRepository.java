package com.uade.tpo.demo.repository;

import java.util.List;
import com.uade.tpo.demo.entity.Animals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalsRepository extends JpaRepository<Animals, Long>{
    @Query("SELECT a FROM Animals a WHERE a.name = ?1")
    List<Animals>findByName(String name);
}
