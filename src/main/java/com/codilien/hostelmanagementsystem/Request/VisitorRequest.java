package com.codilien.hostelmanagementsystem.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitorRequest {

    @NotNull(message = "Visitor name cannot be null.")
    @Size(min = 2, max = 50, message = "Visitor name must be between 2 and 50 characters.")
    @Pattern(
            regexp = "^[a-zA-Z\\s]+$",
            message = "Visitor name must contain only letters and spaces."
    )
    private String visitorName;



    @NotNull(message = "Visit start time cannot be null.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitStart;

//    @NotNull(message = "Visit end time cannot be null.")
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime visitEnd;

    @NotNull(message = "Visit purpose cannot be null.")
    @Size(min = 5, max = 100, message = "Visit purpose must be between 5 and 100 characters.")
    private String visitPurpose;

    @NotNull(message = "Visitor contact cannot be null.")
    @Pattern(
            regexp = "^\\+?[0-9]{1,3}\\s?[0-9]{6,14}$",
            message = "Invalid phone number format"
    )
    private String visitorContact;

    @NotNull(message = "Associated student ID cannot be null.")
    @Positive(message = "Associated student ID must be a positive number.")
    private Long associatedStudentId;

    public boolean isVisitEndAfterStart() {
        return visitEnd == null || visitStart == null || !visitEnd.isBefore(visitStart);
    }
}
