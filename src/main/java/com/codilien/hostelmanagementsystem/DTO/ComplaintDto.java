package com.codilien.hostelmanagementsystem.DTO;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {
    private Long Id;

    @PastOrPresent
    private LocalDate dateFiled;
    private String topic;
    private String description;
    private LocalDate dateResolved;
    private String severityLevel;
    private boolean isResolved;

    private Long studentId;
}
