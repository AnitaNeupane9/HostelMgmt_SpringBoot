package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.DTO.ComplaintDto;
import com.codilien.hostelmanagementsystem.model.Complaint;
import com.codilien.hostelmanagementsystem.model.Student;
import com.codilien.hostelmanagementsystem.repository.ComplaintRepository;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import com.codilien.hostelmanagementsystem.service.ComplaintService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository, StudentRepository studentRepository) {
        this.complaintRepository = complaintRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    @PreAuthorize("hasRole('STUDENT')")
    public ComplaintDto createComplaint(ComplaintDto complaintDto) {

        Optional<Student> studentOpt = studentRepository.findById(complaintDto.getStudentId());
        if (studentOpt.isEmpty()) {
            throw new ResourceNotFoundException("Student");
        }

        Complaint complaint = new Complaint();
        complaint.setId(complaintDto.getId());
        complaint.setDateFiled(complaintDto.getDateFiled());
        complaint.setTopic(complaintDto.getTopic());
        complaint.setDescription(complaintDto.getDescription());
        complaint.setDateResolved(null);
        complaint.setSeverityLevel(complaintDto.getSeverityLevel());
        complaint.setResolved(false);
        complaint.setStudent(studentOpt.get());

        Complaint savedComplaint = complaintRepository.save(complaint);
        return mapToDTO(savedComplaint);
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN') or ((hasRole('STUDENT') and @securityService.isOwner(#id,'COMPLAINT')))")
    public ComplaintDto getComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint"));
        return mapToDTO(complaint);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')or ((hasRole('STUDENT') and @securityService.isOwner(#id,'COMPLAINT')))")
    public List<ComplaintDto> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream()
                .map(complaint -> mapToDTO(complaint))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public void deleteComplaints(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint"));
        complaintRepository.delete(complaint);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ComplaintDto updateComplaint(Long id, ComplaintDto complaintDto, HttpSession session) {
        Complaint toBeUpdated = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint"));
//        toBeUpdated.setDateFiled(complaintDto.getDateFiled());
//        toBeUpdated.setTopic(complaintDto.getTopic());
//        toBeUpdated.setDescription(complaintDto.getDescription());
        toBeUpdated.setSeverityLevel(complaintDto.getSeverityLevel());
        toBeUpdated.setDateResolved(complaintDto.getDateResolved());
        toBeUpdated.setResolved(complaintDto.isResolved());

        Complaint updatedComplaint = complaintRepository.save(toBeUpdated);
        return mapToDTO(updatedComplaint);
    }

    // Helper method to map Complaint entity to ComplaintDTO
    private ComplaintDto mapToDTO(Complaint complaint) {
        ComplaintDto dto = new ComplaintDto();
        dto.setId(complaint.getId());
        dto.setDateFiled(complaint.getDateFiled());
        dto.setTopic(complaint.getTopic());
        dto.setDescription(complaint.getDescription());
        dto.setSeverityLevel(complaint.getSeverityLevel());
        dto.setResolved(complaint.isResolved());
        dto.setDateResolved(complaint.getDateResolved());
        dto.setStudentId(complaint.getStudent().getId());
        return dto;
    }
}
