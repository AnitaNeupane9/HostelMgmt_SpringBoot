package com.codilien.hostelmanagementsystem.model;

import com.codilien.hostelmanagementsystem.Auditing.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HostelReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int rating; // Range 1-5

    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false, length = 1000)
    private String content;

    private int likes = 0;
    private int dislikes = 0;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @OneToMany(mappedBy = "hostelReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewReply> replies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "hostel_review_liked_by_students",
            joinColumns = @JoinColumn(name = "hostel_review_id")
    )
    private Set<Long> likedByStudents = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "hostel_review_disliked_by_students",
            joinColumns = @JoinColumn(name = "hostel_review_id")
    )
    private Set<Long> dislikedByStudents = new HashSet<>();

}
