package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> getUserByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findById(int id);
    boolean existsById(int id);
    void deleteById(int id);

}
