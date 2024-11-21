package com.codilien.hostelmanagementsystem.service;

import com.codilien.hostelmanagementsystem.DTO.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto registerStudent (StudentDto studentDto);
    StudentDto getStudent (Long id);
    List<StudentDto> getAllStudent();
    StudentDto updateStudent(Long id, StudentDto studentDto);
    void deleteStudent(Long id);

}
