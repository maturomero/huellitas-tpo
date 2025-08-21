package com.uade.tpo.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.dto.CategoryRequest;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;
import com.uade.tpo.demo.exceptions.CategoryNotExistException;
import com.uade.tpo.demo.service.CategoryServiceImpl;


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
@RequestMapping("categories")
public class CategoriesController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    public ResponseEntity<List <Category>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }
    
            
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> result = categoryService.getCategoryById(categoryId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.notFound().build();
    }             


    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest categoryRequest)
            throws CategoryDuplicateException {
        Category result = categoryService.createCategory(categoryRequest.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity <Void> deleteByCategory(@PathVariable Long categoryId) throws CategoryNotExistException{
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }


    

    @PatchMapping("/{categoryId}")
    public ResponseEntity <Category> editByCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest) throws CategoryDuplicateException, CategoryNotExistException{
        Category result =  categoryService.editCategory(categoryId, categoryRequest.getDescription());
        return ResponseEntity.ok(result);

    }
}
