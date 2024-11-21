package com.codilien.hostelmanagementsystem.model;

import com.codilien.hostelmanagementsystem.Auditing.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Complaint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate dateFiled;
    private String topic;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateResolved;
    private String severityLevel;
    private boolean isResolved;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;        // Many complaints can be filed by one student.
}
