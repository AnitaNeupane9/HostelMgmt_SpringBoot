package com.codilien.hostelmanagementsystem.service;

import com.codilien.hostelmanagementsystem.DTO.ComplaintDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ComplaintService {
    ComplaintDto createComplaint(ComplaintDto complaintDto);
    ComplaintDto getComplaint(Long id);
    List<ComplaintDto> getAllComplaints();
    void deleteComplaints(Long id);
    ComplaintDto updateComplaint(Long id, ComplaintDto complaintDto, HttpSession session);
}
