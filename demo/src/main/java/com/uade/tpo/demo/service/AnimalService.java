package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Animal;
import com.uade.tpo.demo.exceptions.AnimalDuplicateException;
import com.uade.tpo.demo.exceptions.AnimalNotExistException;
import com.uade.tpo.demo.exceptions.NoEntitiesFoundException;

@Service
public interface AnimalService {
    public List<Animal> getAnimals() throws NoEntitiesFoundException;
    public Optional<Animal> getAnimalById(Long id) throws AnimalNotExistException;
    public Animal createAnimals(String name) throws AnimalDuplicateException;
    public void deleteAnimal(long id) throws AnimalNotExistException;
    public Animal editAnimal(Long id, String newName) throws AnimalDuplicateException, AnimalNotExistException;
}
