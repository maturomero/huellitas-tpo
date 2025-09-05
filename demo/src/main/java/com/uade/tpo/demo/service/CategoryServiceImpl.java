package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;
import com.uade.tpo.demo.exceptions.CategoryNotExistException;
import com.uade.tpo.demo.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private  CategoryRepository repo;

    // Listamos toda las categorias
    public List<Category> getCategories() {
        return repo.findAll();
    }

    // Buscamos por algun ID especidifco
    public Optional<Category> getCategoryById(Long id) {
        return repo.findById(id);
    }

    // Crear la nueva categoria que queramos
    public Category createCategory(String description) throws CategoryDuplicateException {

        if (!repo.findByDescription(description).isEmpty()){
            throw new CategoryDuplicateException();
        }
        Category category = new Category();
        category.setDescription(description);
        return repo.save(category);
    }

    // Eliminar por id
    public void deleteCategory(long id) throws CategoryNotExistException {
        Optional<Category> c = repo.findById(id);
        if(c.isEmpty()){
            throw new CategoryNotExistException();
        }
        repo.deleteById(id);
    }


    //Editar por categoria por id
    public Category editCategory(Long id, String newDescription) throws CategoryDuplicateException, CategoryNotExistException{
        
        Optional<Category> c = repo.findById(id);

        if (c.isEmpty()){
            throw new CategoryNotExistException();
        }
        if(!repo.findByDescription(newDescription).isEmpty()){
            throw new CategoryDuplicateException();
        }

        Category category = c.get();
        category.setDescription(newDescription);

        return repo.save(category);
    }
}