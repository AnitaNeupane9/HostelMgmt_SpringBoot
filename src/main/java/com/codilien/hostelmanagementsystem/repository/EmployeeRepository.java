package com.codilien.hostelmanagementsystem.repository;


import com.codilien.hostelmanagementsystem.Enum.Role;
import com.codilien.hostelmanagementsystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByUsername(String username);
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByRole(Role role);

    @Query("SELECT e FROM Employee e WHERE e.employmentEndDate < :currentDate")
    List<Employee> findInactive(@Param("currentDate")LocalDate currentDate);
}
