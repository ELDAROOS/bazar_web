package com.bazarweb.bazarweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bazarweb.bazarweb.model.User;

@Repository
public interface AdminRepository extends JpaRepository<User, Integer> {
}
