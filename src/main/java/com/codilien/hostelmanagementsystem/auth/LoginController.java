package com.codilien.hostelmanagementsystem.auth;

import com.codilien.hostelmanagementsystem.Enum.Role;
import com.codilien.hostelmanagementsystem.Response.BaseResponse;
import com.codilien.hostelmanagementsystem.ServiceImpl.EmployeeServiceImpl;
import com.codilien.hostelmanagementsystem.ServiceImpl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4209")
public class LoginController {

    @Autowired
    private AuthService authService;

//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//        return authService.verify(loginRequest);
//    }

@PostMapping("/login")
public ResponseEntity<BaseResponse<?>> login(@RequestBody LoginRequest loginRequest) {
    BaseResponse<?> response = authService.verify(loginRequest);
    return ResponseEntity.status(response.getCode()).body(response);
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

