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
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomAllotment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent(message = "Allotment cannot be in the future.")
    private LocalDate allotmentDate;
    private String duration;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PastOrPresent(message = "CheckIn date cannot be in the future.")
    private LocalDateTime checkIn;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PastOrPresent(message = "Checkout date cannot be in the future.")
    private LocalDateTime checkOut;

    @OneToOne
    @JoinColumn(name = "studentId", unique = true)
    private Student student; // One student has one room allotment

    @ManyToOne
    @JoinColumn(name = "roomId")
    private RoomDetails roomDetails; // Many room allotments can belong to one room

}
