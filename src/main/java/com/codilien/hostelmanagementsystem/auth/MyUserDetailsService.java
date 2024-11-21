package com.codilien.hostelmanagementsystem.auth;

import com.codilien.hostelmanagementsystem.model.Employee;
import com.codilien.hostelmanagementsystem.model.EmployeePrincipal;
import com.codilien.hostelmanagementsystem.model.Student;
import com.codilien.hostelmanagementsystem.model.StudentPrincipal;
import com.codilien.hostelmanagementsystem.repository.EmployeeRepository;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find the student first
        Student student = studentRepository.findByUsername(username);
        if (student != null) {
            return new StudentPrincipal(student);
        }

        // If student is not found, try to find the employee
        Employee employee = employeeRepository.findByUsername(username);

        if (employee != null) {
            return new EmployeePrincipal(employee);
        }

        // If neither student nor employee is found, throw an exception
        System.out.println("User not found");
        throw new UsernameNotFoundException("User not found.");
    }
}
