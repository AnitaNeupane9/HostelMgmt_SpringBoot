package com.codilien.hostelmanagementsystem.service;

import com.codilien.hostelmanagementsystem.DTO.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployee(Long id);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployees();
    void deleteEmployee(Long id);

}
