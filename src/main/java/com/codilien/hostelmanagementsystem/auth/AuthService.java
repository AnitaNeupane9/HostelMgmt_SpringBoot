package com.codilien.hostelmanagementsystem.auth;

import com.codilien.hostelmanagementsystem.Enum.Role;
import com.codilien.hostelmanagementsystem.Response.BaseResponse;
import com.codilien.hostelmanagementsystem.model.EmployeePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

//    public String verify(LoginRequest loginRequest) {
//
//        String entityType = null;
//        Role role = null;
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
//
//        Authentication authentication =
//                authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//            if (userDetails instanceof EmployeePrincipal) {
//                entityType = "Employee";
//                role = ((EmployeePrincipal) userDetails).getRole();
//            } else {
//                entityType = "Student";
//            }
//            return jwtService.generateToken(loginRequest.getUsername(), entityType, role);
//        }
//        return "Fail";
//    }

    public BaseResponse<?> verify(LoginRequest loginRequest) {
        BaseResponse<Object> response = new BaseResponse<>();
        String entityType = null;
        Role role = null;

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                if (userDetails instanceof EmployeePrincipal) {
                    entityType = "Employee";
                    role = ((EmployeePrincipal) userDetails).getRole();
                } else {
                    entityType = "Student";
                }

                String token = jwtService.generateToken(loginRequest.getUsername(), entityType, role);

                response.setCode(200);
                response.setMessage("Login successful");
                response.setStatus(true);
                response.setData(token);
            } else {
                response.setCode(401);
                response.setMessage("Authentication failed");
                response.setStatus(false);
                response.setData(null);
            }
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("An error occurred.");
            response.setStatus(false);
            response.setData(null);
        }

        return response;
    }

}
