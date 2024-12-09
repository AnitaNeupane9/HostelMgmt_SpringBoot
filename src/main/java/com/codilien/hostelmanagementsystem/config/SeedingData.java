package com.codilien.hostelmanagementsystem.config;

import com.codilien.hostelmanagementsystem.Enum.Role;
import com.codilien.hostelmanagementsystem.model.Employee;
import com.codilien.hostelmanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedingData implements CommandLineRunner {

    private EmployeeRepository employeeRepository;

    @Autowired
    public SeedingData(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public void run(String... args) throws Exception {

        Optional<Employee> existingAdmin = employeeRepository.findByRole(Role.ADMIN);
        if (existingAdmin.isEmpty()){
            Employee admin = new Employee();
            admin.setName("Admin");
            admin.setUsername("alien@gmail.com");
            admin.setPassword(encoder.encode("alien9"));
            admin.setAddress("Rasuwa");
            admin.setContactNumber("+977 9845007443");
            admin.setRole(Role.ADMIN);

            employeeRepository.save(admin);
        }

    }
}
