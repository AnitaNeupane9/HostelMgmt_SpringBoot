package com.codilien.hostelmanagementsystem.auth;

import com.codilien.hostelmanagementsystem.Enum.Role;
import com.codilien.hostelmanagementsystem.ServiceImpl.EmployeeServiceImpl;
import com.codilien.hostelmanagementsystem.ServiceImpl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService.verify(loginRequest);
    }
}



//@RestController
//public class LoginController {
//
//    @Autowired
//    private StudentServiceImpl studentService;
//
//    @Autowired
//    private EmployeeServiceImpl employeeService;
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//        Role role = loginRequest.getRole();
//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//
//        if ("STUDENT".equalsIgnoreCase(String.valueOf(role))) {
//            // Delegate to StudentService
//            return studentService.verify(username, password);
//        } else {
//            // Assume all other roles are employee roles
//            return employeeService.verify(username, password, role);
//        }
//    }
//}

