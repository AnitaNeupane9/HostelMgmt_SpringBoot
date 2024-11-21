package com.codilien.hostelmanagementsystem.mapper;

import com.codilien.hostelmanagementsystem.DTO.StudentDto;
import com.codilien.hostelmanagementsystem.model.Student;

public class StudentMapper {
    public static Student mapToStudent (StudentDto studentDto){
        Student student = new Student(
                studentDto.getId(),
                studentDto.getName(),
                studentDto.getGender(),
                studentDto.getAddress(),
                studentDto.getNationality(),
                studentDto.getDateOfBirth(),
                studentDto.getContactNumber(),
                studentDto.getUsername(),
                studentDto.getParentContact(),
                studentDto.getEmergencyContact(),
                studentDto.getIdProof(),
//                studentDto.getProfilePicture(),
//                null,
                studentDto.getPassword(),
                studentDto.isActive()
        );
        return student;
    }

    public static StudentDto mapToStudentDto(Student student){
        StudentDto studentDto  = new StudentDto(
                student.getId(),
                student.getName(),
                student.getGender(),
                student.getAddress(),
                student.getNationality(),
                student.getDateOfBirth(),
                student.getContactNumber(),
                student.getUsername(),
                student.getParentContact(),
                student.getEmergencyContact(),
                student.getIdProof(),
//                student.getProfilePicture(),
                student.getPassword(),
                student.isActive()
        );
        return studentDto;
    }
}


//package com.codilien.hotelmanagement.mapper;
//
//import com.codilien.hotelmanagement.DTO.StudentDto;
//import com.codilien.hotelmanagement.model.Student;
//
//public class StudentMapper {
//    public static Student mapToStudent (StudentDto studentDto){
//        Student student = new Student(
//                studentDto.getId(),
//                studentDto.getName(),
//                studentDto.getGender(),
//                studentDto.getAddress(),
//                studentDto.getNationality(),
//                studentDto.getDateOfBirth(),
//                studentDto.getContactNumber(),
//                studentDto.getEmail(),
//                studentDto.getParentContact(),
//                studentDto.getEmergencyContact(),
//                studentDto.getIdProof(),
////                studentDto.getProfilePicture(),
//                null,
//                studentDto.getPassword(),
//                studentDto.isActive()
//        );
//        return student;
//    }
//
//    public static StudentDto mapToStudentDto(Student student){
//        StudentDto studentDto  = new StudentDto(
//                student.getId(),
//                student.getName(),
//                student.getGender(),
//                student.getAddress(),
//                student.getNationality(),
//                student.getDateOfBirth(),
//                student.getContactNumber(),
//                student.getEmail(),
//                student.getParentContact(),
//                student.getEmergencyContact(),
//                student.getIdProof(),
//                null,
//                student.getProfilePicture(),
//                student.getPassword(),
//                student.isActive()
//        );
//        return studentDto;
//    }
//}
