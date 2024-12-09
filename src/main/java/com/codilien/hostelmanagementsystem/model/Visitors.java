package com.codilien.hostelmanagementsystem.model;

import com.codilien.hostelmanagementsystem.Auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Visitors extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactInfo;
    private LocalDateTime checkedIn;
    private LocalDateTime checkedOut;
    private String purposeOfVisit;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
}

