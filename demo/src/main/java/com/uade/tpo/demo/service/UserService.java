package com.uade.tpo.demo.service;

import java.util.Optional;

import com.uade.tpo.demo.entity.User;

public interface UserService {
    public Optional<User> deleteUser(Long id);
    public Optional<User> getUserById(Long id);
}
