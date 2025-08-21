package com.uade.tpo.demo.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "La categoria no existe")
public class CategoryNotExistException extends Exception {
    
}
