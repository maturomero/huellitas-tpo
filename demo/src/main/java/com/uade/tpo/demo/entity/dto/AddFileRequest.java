package com.uade.tpo.demo.entity.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddFileRequest {
    private String name;
    private MultipartFile file;
}