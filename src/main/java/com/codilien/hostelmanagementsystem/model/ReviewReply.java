package com.codilien.hostelmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReviewReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private HostelReview hostelReview;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

//
//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
}
