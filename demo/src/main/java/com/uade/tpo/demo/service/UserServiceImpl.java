package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.demo.exceptions.UserDuplicateException;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.UserRepository;

@Service
public class UserServiceImpl{
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String fullname, String email, String password) throws UserDuplicateException {
        if (!userRepository.findByEmail(email).isEmpty()) {
            throw new UserDuplicateException();
        }

        User user = new User(fullname, email, password);
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        List<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        if (user.get(0).getPassword().equals(password)) {
            return Optional.of(user.get(0));
        }

        return Optional.empty();
    }
    

    public Optional<User> deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return Optional.empty();
        }

        user.get().setStatus(false);
        userRepository.save(user.get()); 

        return user;
    }

    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return Optional.empty();
        }
        return user;
    }
}
