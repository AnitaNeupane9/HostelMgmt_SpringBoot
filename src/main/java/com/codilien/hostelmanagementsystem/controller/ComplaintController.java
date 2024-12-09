package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.ComplaintDto;
import com.codilien.hostelmanagementsystem.constants.ApiPaths;
import com.codilien.hostelmanagementsystem.service.ComplaintService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_COMPLAINT_API)
public class ComplaintController {

    private final ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping(ApiPaths.CREATE_COMPLAINT)
    public ResponseEntity<ComplaintDto> addComplaint(@RequestBody ComplaintDto complaintDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintService.createComplaint(complaintDto));
    }


    @GetMapping(ApiPaths.GET_COMPLAINT_BY_ID)
    public ResponseEntity<ComplaintDto> getComplaint(@PathVariable Long id) {
        ComplaintDto complaintDto = complaintService.getComplaint(id);
        return new ResponseEntity<>(complaintDto, HttpStatus.OK);
    }

    @GetMapping(ApiPaths.GET_ALL_COMPLAINTS)
    public ResponseEntity<List<ComplaintDto>> getAllComplaint (){
        List<ComplaintDto> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    @PutMapping(ApiPaths.UPDATE_COMPLAINT)
    public ResponseEntity<ComplaintDto> updateComplaint(@PathVariable Long id, @RequestBody ComplaintDto complaintDto, HttpSession session){
        return ResponseEntity.status(HttpStatus.OK).body(complaintService.updateComplaint(id, complaintDto, session));
    }


    @DeleteMapping(ApiPaths.DELETE_COMPLAINT)
    public ResponseEntity<String> deleteComplaint(Long id){
        complaintService.deleteComplaints(id);
        return ResponseEntity.ok("Complaint is deleted successfully.");
    }
}
