package com.codilien.hostelmanagementsystem.mapper;


import com.codilien.hostelmanagementsystem.DTO.EmployeeDto;
import com.codilien.hostelmanagementsystem.model.Employee;

public class EmployeeMapper {

    // Map from EmployeeDto to Employee
    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getName(),
                employeeDto.getContactNumber(),
                employeeDto.getAddress(),
                employeeDto.getUsername(),
                employeeDto.getRole(),
                employeeDto.getPassword(),
                employeeDto.getHireDate(),
                employeeDto.getEmploymentEndDate(),
                employeeDto.getShiftStartsAt(),
                employeeDto.getShiftEndsAt(),
                employeeDto.getIdProof()
        );
        return employee;
    }

    // Map from Employee to EmployeeDto
    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getContactNumber(),
                employee.getAddress(),
                employee.getUsername(),
                employee.getRole(),
                employee.getPassword(),
                employee.getHireDate(),
                employee.getEmploymentEndDate(),
                employee.getShiftStartsAt(),
                employee.getShiftEndsAt(),
                employee.getIdProof()
        );
    }
}
