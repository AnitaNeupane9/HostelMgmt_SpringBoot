package com.codilien.hostelmanagementsystem.config;

import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.model.*;
import com.codilien.hostelmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final RoomDetailsRepository roomDetailsRepository;
    private StudentRepository studentRepository;
    private EmployeeRepository employeeRepository;
    private ComplaintRepository complaintRepository;
    private FeeRepository feeRepository;
    private ReviewReplyRepository reviewReplyRepository;
    private HostelReviewRepository hostelReviewRepository;

    @Autowired
    public SecurityService(
            StudentRepository studentRepository,
            EmployeeRepository employeeRepository,
            ComplaintRepository complaintRepository,
            FeeRepository feeRepository,
            ReviewReplyRepository reviewReplyRepository,
            HostelReviewRepository hostelReviewRepository, RoomDetailsRepository roomDetailsRepository) {

        this.studentRepository = studentRepository;
        this.employeeRepository = employeeRepository;
        this.complaintRepository = complaintRepository;
        this.feeRepository = feeRepository;
        this.reviewReplyRepository = reviewReplyRepository;
        this.hostelReviewRepository = hostelReviewRepository;
        this.roomDetailsRepository = roomDetailsRepository;
    }

    public boolean isOwner(Long entityId, String entityType) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        switch (entityType) {
            case "STUDENT":
                Student  student = studentRepository.findById(entityId)
                        .orElse(null);
                if (student == null || !(student.getUsername().equals(currentUsername))){
                    throw new AccessDeniedException("Access is denied. Try Again!");
                }
                break;

            case "EMPLOYEE":
                Employee employee = employeeRepository.findById(entityId)
                        .orElse(null);
                if (employee == null || !(employee.getUsername().equals(currentUsername))) {
                    throw new AccessDeniedException("Access is denied. Try Again!");
                }
                break;

            case "COMPLAINT":
                Complaint complaint = complaintRepository.findById(entityId)
                        .orElse(null);
                if (complaint == null || !(complaint.getStudent().getUsername().equals(currentUsername))) {
                    throw new AccessDeniedException("Access is denied. Try Again!");
                }
                break;

            case "FEE":
                Fee fee = feeRepository.findById(entityId)
                        .orElse(null);
                if (fee == null || !(fee.getStudent().getUsername().equals(currentUsername))){
                    throw new AccessDeniedException("Access is denied. Try Again!");
                }
                break;

            case "REPLY":
                ReviewReply reviewReply = reviewReplyRepository.findById(entityId)
                        .orElse(null);
                if (reviewReply == null || !(reviewReply.getStudent().getUsername().equals(currentUsername))){
                    throw new AccessDeniedException("Access is denied. Try Again!");
                }
                break;

            case "REVIEW":
                HostelReview hostelReview = hostelReviewRepository.findById(entityId)
                        .orElse(null);
                if (hostelReview == null || !(hostelReview.getStudent().getUsername().equals(currentUsername))){
                    throw new AccessDeniedException("Access is denied. Try Again!");
                }
                break;

            case "ROOM":
                RoomDetails roomDetails = roomDetailsRepository.findById(entityId)
                        .orElse(null);
                if (roomDetails == null) {
                    throw new ResourceNotFoundException("Room");
                }

                boolean isOwner = false;

                // Iterate through room allotments to check ownership
                for (RoomAllotment allotment : roomDetails.getRoomAllotments()) {
                    if (allotment.getStudent() != null && allotment.getStudent().getUsername().equals(currentUsername)) {
                        isOwner = true;
                        break;
                    }
                }
                if (!isOwner) {
                    throw new AccessDeniedException("Access is denied.Try again!");
                }
                break;


            default:
                throw new IllegalArgumentException("Unsupported entity type for ownership check.");
        }
        // Ownership confirmed
        return true;
    }
}
