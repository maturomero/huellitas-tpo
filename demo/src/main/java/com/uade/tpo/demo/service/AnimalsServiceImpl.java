package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Animals;
import com.uade.tpo.demo.exceptions.AnimalsDuplicateException;
import com.uade.tpo.demo.exceptions.AnimalsNotExistException;
import com.uade.tpo.demo.repository.AnimalsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalsServiceImpl {
    @Autowired
    private  AnimalsRepository repo;

    // Listamos toda las categorias
    public List<Animals> getCategories() {
        return repo.findAll();
    }

    // Buscamos por algun ID especidifco
    public Optional<Animals> getAnimalsById(Long id){
        return repo.findById(id);
    }

    // Crear la nueva categoria que queramos
    public Animals createAnimals(String name) throws AnimalsDuplicateException {

        if (!repo.findByName(name).isEmpty()){
            throw new AnimalsDuplicateException();
        }
        Animals Animals = new Animals();
        Animals.setName(name);
        return repo.save(Animals);
    }

    // Eliminar por id
    public void deleteAnimals(long id) throws AnimalsNotExistException {
        Optional<Animals> c = repo.findById(id);
        if(c.isEmpty()){
            throw new AnimalsNotExistException();
        }
        repo.deleteById(id);
    }


    //Editar por categoria por id
    public Animals editAnimals(Long id, String newName) throws AnimalsDuplicateException, AnimalsNotExistException{
        
        Optional<Animals> c = repo.findById(id);

        if (c.isEmpty()){
            throw new AnimalsNotExistException();
        }
        if(!repo.findByName(newName).isEmpty()){
            throw new AnimalsDuplicateException();
        }

        Animals Animals = c.get();
        Animals.setName(newName);

        return repo.save(Animals);
    }
}