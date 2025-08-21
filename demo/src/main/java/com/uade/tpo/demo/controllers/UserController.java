package com.uade.tpo.demo.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.UserResponse;
import com.uade.tpo.demo.entity.dto.UserRequest;
import com.uade.tpo.demo.exceptions.UserDuplicateException;
import com.uade.tpo.demo.service.UserServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) throws UserDuplicateException {
        User user = userService.registerUser(userRequest.getFullname(), userRequest.getEmail(), userRequest.getPassword());
        UserResponse userResponse = new UserResponse(user.getId(), user.getFullname(), user.getEmail(), user.isAdmin());
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest loginRequest) {
        Optional<User> optionalUser = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserResponse userResponse = new UserResponse(user.getId(), user.getFullname(), user.getEmail(), user.isAdmin());
            return ResponseEntity.ok(userResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.deleteUser(id);
        
        if (user.isPresent()) {
            return ResponseEntity.ok("Usuario eliminado con exito");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserId(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
            }
            return ResponseEntity.notFound().build() ;
        }

        }



    

