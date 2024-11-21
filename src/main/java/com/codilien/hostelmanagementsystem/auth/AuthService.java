package com.codilien.hostelmanagementsystem.auth;

import com.codilien.hostelmanagementsystem.Enum.Role;
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

    public String verify(LoginRequest loginRequest) {

        String entityType = null;
        Role role = null;

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            if (userDetails instanceof EmployeePrincipal) {
                entityType = "Employee";
                role = ((EmployeePrincipal) userDetails).getRole();
            } else {
                entityType = "Student";
            }
            return jwtService.generateToken(loginRequest.getUsername(), entityType, role);
        }
        return "Fail";
    }
}
