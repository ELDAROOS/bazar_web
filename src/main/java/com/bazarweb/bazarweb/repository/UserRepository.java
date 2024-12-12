package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
