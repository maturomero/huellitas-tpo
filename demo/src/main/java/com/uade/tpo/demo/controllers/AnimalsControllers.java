package com.uade.tpo.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Animal;
import com.uade.tpo.demo.entity.dto.AnimalRequest;
import com.uade.tpo.demo.exceptions.AnimalDuplicateException;
import com.uade.tpo.demo.exceptions.AnimalNotExistException;
import com.uade.tpo.demo.service.AnimalService;


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
    private AnimalService AnimalService;

    @GetMapping
    public ResponseEntity<List <Animal>> getAnimals(){
        return ResponseEntity.ok(AnimalService.getAnimals());
    }
    
            
    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long animalId) throws AnimalNotExistException {
        Optional<Animal> result = AnimalService.getAnimalById(animalId);
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


    @DeleteMapping("/{animalId}")
    public ResponseEntity <Void> deleteByAnimal(@PathVariable Long animalId) throws AnimalNotExistException{
        AnimalService.deleteAnimal(animalId);
        return ResponseEntity.noContent().build();
    }


    

    @PatchMapping("/{animalId}")
    public ResponseEntity <Animal> editByAnimals(@PathVariable Long animalId, @RequestBody AnimalRequest AnimalRequest) throws AnimalDuplicateException, AnimalNotExistException{
        Animal result =  AnimalService.editAnimal(animalId, AnimalRequest.getName());
        return ResponseEntity.ok(result);

    }
}

