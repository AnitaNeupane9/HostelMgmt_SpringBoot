package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.FeeDto;
import com.codilien.hostelmanagementsystem.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/create")
    public ResponseEntity<FeeDto> addFee(@RequestBody FeeDto feeDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFee(feeDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeDto> getFee(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(feeService.getFee(id));
    }

    @GetMapping({"/", " "})
    public ResponseEntity<List<FeeDto>> getAllFee(){
        List<FeeDto>  fees = feeService.getAllFee();
        return ResponseEntity.ok(fees);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<FeeDto> updateFee(@PathVariable Long id, @RequestBody FeeDto feeDto){
        return ResponseEntity.status(HttpStatus.OK).body(feeService.updateFee(id, feeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFee(@PathVariable Long id){
        feeService.deleteFee(id);
        return ResponseEntity.ok("Fee record deleted successfully.");
    }
}
