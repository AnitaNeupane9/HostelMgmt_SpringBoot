package com.codilien.hostelmanagementsystem.model;


import com.codilien.hostelmanagementsystem.Auditing.BaseEntity;
import com.codilien.hostelmanagementsystem.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactNumber;
    private String address;
    @Column(columnDefinition = "COLLATE utf8_bin", nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;
    private LocalDate hireDate;
    private LocalDate employmentEndDate;

    private LocalTime shiftStartsAt;
    private LocalTime shiftEndsAt;
    private String idProof;
//    private boolean isActive;
}
