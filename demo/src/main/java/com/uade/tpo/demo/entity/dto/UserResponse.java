package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data

public class UserResponse {
    
    private Long id;
    private String fullname;
    private String email;
    
    public UserResponse(Long id, String fullname, String email){
        this.id = id;
        this.fullname = fullname;
        this.email = email;
    }

}
