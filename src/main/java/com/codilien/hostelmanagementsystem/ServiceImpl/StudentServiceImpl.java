package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.exception.ResourceConflictException;
import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.DTO.StudentDto;
import com.codilien.hostelmanagementsystem.Enum.Role;
import com.codilien.hostelmanagementsystem.mapper.StudentMapper;
import com.codilien.hostelmanagementsystem.model.Student;
import com.codilien.hostelmanagementsystem.repository.EmployeeRepository;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import com.codilien.hostelmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public StudentServiceImpl(StudentRepository studentRepository, EmployeeRepository employeeRepository) {
        this.studentRepository = studentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public StudentDto registerStudent(StudentDto studentDto) {

        Student student = StudentMapper.mapToStudent(studentDto);

        if (studentRepository.existsByUsername(studentDto.getUsername()) || employeeRepository.existsByUsername(studentDto.getUsername())){
            throw new ResourceConflictException("The username " + studentDto.getUsername());
        }

        student.setPassword(encoder.encode(studentDto.getPassword()));

        // Setting the Role and isActive as STUDENT and true
        student.setRole(Role.STUDENT);
        student.setActive(true);

        Student registeredStudent = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(registeredStudent);

    }

//    @Override
//    public StudentDto registerStudent(StudentDto studentDto) {
//
//        Student student = StudentMapper.mapToStudent(studentDto);
//
//        // Check if email is already in use
//        if (studentRepository.existsByEmail(studentDto.getEmail()) || employeeRepository.existsByEmail(studentDto.getEmail())) {
//            throw new IllegalArgumentException("Email is already in use.");
//        }
//
//        // file upload and get the file path
//        String profilePicturePath = storeProfilePicture(studentDto.getProfilePicture());
//        student.setProfilePicture(profilePicturePath);
//
//        student.setRole(Role.STUDENT);
//
//        Student registeredStudent = studentRepository.save(student);
//        return StudentMapper.mapToStudentDto(registeredStudent);
//    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN') or (hasRole('STUDENT') and @securityService.isOwner(#id,'STUDENT'))")
//    @PreAuthorize("hasAnyRole('ADMIN','WARDEN', 'STUDENT')")
    public StudentDto getStudent(Long id)
    {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student"));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public List<StudentDto> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(student -> StudentMapper.mapToStudentDto(student))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN') or (hasRole('STUDENT') and @securityService.isOwner(#id,'STUDENT'))")
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Student toBeUpdated = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student"));

        if (studentRepository.existsByUsername(studentDto.getUsername()) || employeeRepository.existsByUsername(studentDto.getUsername())){
            throw new ResourceConflictException("The username " + studentDto.getUsername());
        }

        toBeUpdated.setName(studentDto.getName());
        toBeUpdated.setGender(studentDto.getGender());
        toBeUpdated.setAddress(studentDto.getAddress());
        toBeUpdated.setNationality(studentDto.getNationality());
        toBeUpdated.setDateOfBirth(studentDto.getDateOfBirth());
        toBeUpdated.setContactNumber(studentDto.getContactNumber());
        toBeUpdated.setUsername(studentDto.getUsername());
        toBeUpdated.setParentContact(studentDto.getParentContact());
        toBeUpdated.setEmergencyContact(studentDto.getEmergencyContact());
        toBeUpdated.setIdProof(studentDto.getIdProof());
        toBeUpdated.setPassword(encoder.encode(studentDto.getPassword()));
        toBeUpdated.setActive(studentDto.isActive());

        Student updatedStudent = studentRepository.save(toBeUpdated);
        return StudentMapper.mapToStudentDto(updatedStudent);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student"));
        studentRepository.delete(student);
    }


//    // method for the storing profile picture
//    private String storeProfilePicture(MultipartFile profilePicture) {
//
//        String uniqueId = UUID.randomUUID().toString();
//        String storageFileName = uniqueId + "_" + profilePicture.getOriginalFilename();
//
//        try {
//            //directory where profile pictures will be stored
//            String uploadDir = "public/images/profilePicture/";
//            Path uploadPath = Paths.get(uploadDir);
//
//            // Create the directory if doesn’t exist
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            // Save the file
//            try (InputStream inputStream = profilePicture.getInputStream()) {
//                Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
//            }
//
//            // Return the complete path to be saved in the database
//            return uploadDir + storageFileName;
//        } catch (Exception ex) {
//            throw new RuntimeException("Failed to store profile picture: " + ex.getMessage());
//        }
//    }
}
