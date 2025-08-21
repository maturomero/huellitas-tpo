package com.uade.tpo.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Animals;
import com.uade.tpo.demo.entity.dto.AnimalsRequest;
import com.uade.tpo.demo.exceptions.AnimalsDuplicateException;
import com.uade.tpo.demo.exceptions.AnimalsNotExistException;
import com.uade.tpo.demo.service.AnimalsServiceImpl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("animals")
public class AnimalsControllers {

    @Autowired
    private AnimalsServiceImpl AnimalsService;

    @GetMapping
    public ResponseEntity<List <Animals>> getCategories(){
        return ResponseEntity.ok(AnimalsService.getCategories());
    }
    
            
    @GetMapping("/{animalsId}")
    public ResponseEntity<Animals> getAnimalsById(@PathVariable Long AnimalsId) {
        Optional<Animals> result = AnimalsService.getAnimalsById(AnimalsId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.notFound().build();
    }             


    @PostMapping
    public ResponseEntity<Object> createAnimals(@RequestBody AnimalsRequest AnimalsRequest)
            throws AnimalsDuplicateException {
        Animals result = AnimalsService.createAnimals(AnimalsRequest.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @DeleteMapping("/{animalsId}")
    public ResponseEntity <Void> deleteByAnimals(@PathVariable Long AnimalsId) throws AnimalsNotExistException{
        AnimalsService.deleteAnimals(AnimalsId);
        return ResponseEntity.noContent().build();
    }


    

    @PatchMapping("/{animalsId}")
    public ResponseEntity <Animals> editByAnimals(@PathVariable Long AnimalsId, @RequestBody AnimalsRequest AnimalsRequest) throws AnimalsDuplicateException, AnimalsNotExistException{
        Animals result =  AnimalsService.editAnimals(AnimalsId, AnimalsRequest.getName());
        return ResponseEntity.ok(result);

    }
}

