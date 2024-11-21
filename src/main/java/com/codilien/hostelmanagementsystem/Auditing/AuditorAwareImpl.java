package com.codilien.hostelmanagementsystem.Auditing;

import com.codilien.hostelmanagementsystem.model.EmployeePrincipal;
import com.codilien.hostelmanagementsystem.model.StudentPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof StudentPrincipal) {
            return Optional.ofNullable(((StudentPrincipal) principal).getUsername());
        } else if (principal instanceof EmployeePrincipal) {
            return Optional.ofNullable(((EmployeePrincipal) principal).getUsername());
        }

        return Optional.empty();
    }
}
