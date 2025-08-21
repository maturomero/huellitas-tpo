package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data

public class UserResponse {
    
    private Long id;
    private String fullname;
    private String email;
    private String password;
    private boolean admin;
    
    public UserResponse(Long id, String fullname, String email, boolean admin){
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.admin = admin;
    }

}
