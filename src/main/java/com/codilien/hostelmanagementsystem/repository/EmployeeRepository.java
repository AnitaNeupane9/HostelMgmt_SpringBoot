package com.codilien.hostelmanagementsystem.repository;


import com.codilien.hostelmanagementsystem.Enum.Role;
import com.codilien.hostelmanagementsystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByUsername(String username);
    Employee findByUsername(String username);
    Optional<Employee> findByRole(Role role);

}
