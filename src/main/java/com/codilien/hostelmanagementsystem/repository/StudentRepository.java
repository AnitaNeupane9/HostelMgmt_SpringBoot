package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByUsername(String username);
    Student findByUsername(String username);
}
