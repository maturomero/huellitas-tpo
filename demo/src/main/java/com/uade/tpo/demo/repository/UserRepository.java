package com.uade.tpo.demo.repository;
import com.uade.tpo.demo.entity.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.status = true")
    List<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.status = true")
    Optional<User> findActiveById(Long id);
}