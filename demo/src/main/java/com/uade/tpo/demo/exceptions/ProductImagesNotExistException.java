package com.uade.tpo.demo.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "La imagen del producto no existe. ")
public class ProductImagesNotExistException extends Exception {}

