package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.EmployeeDto;
import com.codilien.hostelmanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id){
        EmployeeDto employeeDto = employeeService.getEmployee(id);
        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EmployeeDto> editEmployee (@PathVariable Long id, @RequestBody EmployeeDto employeeDto){
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<EmployeeDto>> getAllAccounts() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }

}
