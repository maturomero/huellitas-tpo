package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;
import com.uade.tpo.demo.entity.Category;

import org.springframework.stereotype.Service;

import com.uade.tpo.demo.exceptions.CategoryDuplicateException;
import com.uade.tpo.demo.exceptions.CategoryNotExistException;
import com.uade.tpo.demo.exceptions.NoEntitiesFoundException;

@Service
public interface CategoryService {
    public List<Category> getCategories() throws NoEntitiesFoundException;
    public Optional<Category> getCategoryById(Long id) throws CategoryNotExistException;
    public Category createCategory(String description) throws CategoryDuplicateException;
    public void deleteCategory(long id) throws CategoryNotExistException;
    public Category editCategory(Long id, String newDescription) throws CategoryDuplicateException, CategoryNotExistException;

}
