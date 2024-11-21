package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.ComplaintDto;
import com.codilien.hostelmanagementsystem.service.ComplaintService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/create")
    public ResponseEntity<ComplaintDto> addComplaint(@RequestBody ComplaintDto complaintDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintService.createComplaint(complaintDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaint(@PathVariable Long id) {
        ComplaintDto complaintDto = complaintService.getComplaint(id);
        return new ResponseEntity<>(complaintDto, HttpStatus.OK);
    }

    @GetMapping({"/", " "})
    public ResponseEntity<List<ComplaintDto>> getAllComplaint (){
        List<ComplaintDto> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ComplaintDto> updateComplaint(@PathVariable Long id, @RequestBody ComplaintDto complaintDto, HttpSession session){
        return ResponseEntity.status(HttpStatus.OK).body(complaintService.updateComplaint(id, complaintDto, session));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComplaint(Long id){
        complaintService.deleteComplaints(id);
        return ResponseEntity.ok("Complaint is deleted successfully.");
    }
}
