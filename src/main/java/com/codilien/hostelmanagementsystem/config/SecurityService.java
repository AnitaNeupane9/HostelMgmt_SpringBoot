package com.codilien.hostelmanagementsystem.config;

import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.model.Complaint;
import com.codilien.hostelmanagementsystem.model.Employee;
import com.codilien.hostelmanagementsystem.model.Fee;
import com.codilien.hostelmanagementsystem.model.Student;
import com.codilien.hostelmanagementsystem.repository.ComplaintRepository;
import com.codilien.hostelmanagementsystem.repository.EmployeeRepository;
import com.codilien.hostelmanagementsystem.repository.FeeRepository;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private FeeRepository feeRepository;

    // ownership check logic
    public boolean isOwner(Long entityId, String entityType) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        switch (entityType) {
            case "STUDENT":
                Student  student = studentRepository.findById(entityId)
                        .orElse(null);
                if (student == null || !(student.getUsername().equals(currentUsername))){
                    throw new AccessDeniedException("Access is denied.");
                }
                break;

            case "EMPLOYEE":
                Employee employee = employeeRepository.findById(entityId)
                        .orElse(null);
                if (employee == null || !(employee.getUsername().equals(currentUsername))) {
                    throw new AccessDeniedException("Access is denied.");
                }
                break;

            case "COMPLAINT":
                Complaint complaint = complaintRepository.findById(entityId)
                        .orElse(null);
                if (complaint == null || !(complaint.getStudent().getUsername().equals(currentUsername))) {
                    throw new AccessDeniedException("Access is denied.");
                }
                break;

            case "FEE":
                Fee fee = feeRepository.findById(entityId)
                        .orElse(null);
                if (fee == null || !(fee.getStudent().getUsername().equals(currentUsername))){
                    throw new AccessDeniedException("Access is denied.");
                }

            default:
                throw new IllegalArgumentException("Unsupported entity type for ownership check.");
        }
        // Ownership confirmed
        return true;
    }
}
