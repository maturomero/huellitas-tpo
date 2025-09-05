package com.uade.tpo.demo.service;

import java.util.Optional;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.UserDuplicateException;

public interface UserService {
    public User registerUser(String fullname, String email, String password) throws UserDuplicateException;
    public Optional<User> loginUser(String email, String password);
    public Optional<User> deleteUser(Long id);
    public Optional<User> getUserById(Long id);
}
