package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.exception.ResourceConflictException;
import com.codilien.hostelmanagementsystem.DTO.EmployeeDto;
import com.codilien.hostelmanagementsystem.mapper.EmployeeMapper;
import com.codilien.hostelmanagementsystem.model.Employee;
import com.codilien.hostelmanagementsystem.repository.EmployeeRepository;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import com.codilien.hostelmanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private StudentRepository studentRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, StudentRepository studentRepository) {
        this.employeeRepository = employeeRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        if (employeeRepository.existsByUsername(employeeDto.getUsername()) || studentRepository.existsByUsername(employeeDto.getUsername()))
        {
            throw new ResourceConflictException("The username " + employeeDto.getUsername());
        }

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        employee.setPassword(encoder.encode(employeeDto.getPassword()));
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or ((hasAnyRole('WARDEN', 'ACCOUNTANT') and @securityService.isOwner(#id,'EMPLOYEE')))")
    public EmployeeDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee"));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee"));

        if (employeeRepository.existsByUsername(employeeDto.getUsername()) || studentRepository.existsByUsername(employeeDto.getUsername()))
        {
            throw new ResourceConflictException("The username " + employeeDto.getUsername());
        }

        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setContactNumber(employeeDto.getContactNumber());
        existingEmployee.setAddress(employeeDto.getAddress());
        existingEmployee.setUsername(employeeDto.getUsername());
        existingEmployee.setRole(employeeDto.getRole());
        existingEmployee.setPassword(encoder.encode(employeeDto.getPassword()));
        existingEmployee.setHireDate(employeeDto.getHireDate());
        existingEmployee.setEmploymentEndDate(employeeDto.getEmploymentEndDate());
        existingEmployee.setShiftStartsAt(employeeDto.getShiftStartsAt());
        existingEmployee.setShiftEndsAt(employeeDto.getShiftEndsAt());
        existingEmployee.setIdProof(employeeDto.getIdProof());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> EmployeeMapper.mapToEmployeeDto((employee)))
                .collect(Collectors.toList());
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee"));
        employeeRepository.delete(employee);
    }
}
