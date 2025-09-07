package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Animal;
import com.uade.tpo.demo.exceptions.AnimalDuplicateException;
import com.uade.tpo.demo.exceptions.AnimalNotExistException;
import com.uade.tpo.demo.repository.AnimalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AnimalServiceImpl implements AnimalService{
    @Autowired
    private  AnimalRepository repo;

    // Listamos todos los animales
    public List<Animal> getAnimals() {
        return repo.findAll();
    }


    public Optional<Animal> getAnimalById(Long id) throws AnimalNotExistException{
        Optional<Animal> r = repo.findById(id);
        if(r.isEmpty()){
            throw new AnimalNotExistException();
        }
        return r;
    }


    public Animal createAnimals(String name) throws AnimalDuplicateException {

        if (!repo.findByName(name).isEmpty()){
            throw new AnimalDuplicateException();
        }
        Animal Animals = new Animal();
        Animals.setName(name);
        return repo.save(Animals);
    }


    public void deleteAnimal(long id) throws AnimalNotExistException {
        Optional<Animal> c = repo.findById(id);
        if(c.isEmpty()){
            throw new AnimalNotExistException();
        }
        repo.deleteById(id);
    }


    public Animal editAnimal(Long id, String newName) throws AnimalDuplicateException, AnimalNotExistException{
        
        Optional<Animal> c = repo.findById(id);

        if (c.isEmpty()){
            throw new AnimalNotExistException();
        }
        if(!repo.findByName(newName).isEmpty()){
            throw new AnimalDuplicateException();
        }

        Animal Animals = c.get();
        Animals.setName(newName);

        return repo.save(Animals);
    }
}