package com.uade.tpo.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Animal;
import com.uade.tpo.demo.entity.dto.AnimalRequest;
import com.uade.tpo.demo.exceptions.AnimalDuplicateException;
import com.uade.tpo.demo.exceptions.AnimalNotExistException;
import com.uade.tpo.demo.service.AnimalServiceImpl;


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
    private AnimalServiceImpl AnimalService;

    @GetMapping
    public ResponseEntity<List <Animal>> getAnimals(){
        return ResponseEntity.ok(AnimalService.getAnimals());
    }
    
            
    @GetMapping("/{animalsId}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long AnimalId) {
        Optional<Animal> result = AnimalService.getAnimalById(AnimalId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.notFound().build();
    }             


    @PostMapping
    public ResponseEntity<Object> createAnimal(@RequestBody AnimalRequest AnimalRequest)
            throws AnimalDuplicateException {
        Animal result = AnimalService.createAnimals(AnimalRequest.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @DeleteMapping("/{animalsId}")
    public ResponseEntity <Void> deleteByAnimal(@PathVariable Long AnimalId) throws AnimalNotExistException{
        AnimalService.deleteAnimal(AnimalId);
        return ResponseEntity.noContent().build();
    }


    

    @PatchMapping("/{animalsId}")
    public ResponseEntity <Animal> editByAnimals(@PathVariable Long AnimalsId, @RequestBody AnimalRequest AnimalRequest) throws AnimalDuplicateException, AnimalNotExistException{
        Animal result =  AnimalService.editAnimal(AnimalsId, AnimalRequest.getName());
        return ResponseEntity.ok(result);

    }
}

