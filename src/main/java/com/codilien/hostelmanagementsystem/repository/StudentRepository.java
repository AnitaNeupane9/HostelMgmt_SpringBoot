package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByUsername(String username);
    Optional<Student> findByUsername(String username);
}
