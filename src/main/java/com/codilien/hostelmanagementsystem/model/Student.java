package com.codilien.hostelmanagementsystem.model;

import com.codilien.hostelmanagementsystem.Auditing.BaseEntity;
import com.codilien.hostelmanagementsystem.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(name = "username", nullable = false, unique = true, columnDefinition = "COLLATE utf8_bin")
    private String username;

    @Column(name = "parent_contact")
    private String parentContact;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "id_proof")
    private String idProof;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_active")
    private boolean isActive;


    @OneToOne(mappedBy = "student")
    private RoomAllotment roomAllotment; // One student has one room allotment

    @OneToMany(mappedBy = "student")
    private List<Complaint> complaints; // One student can file multiple complaints

    @OneToOne(mappedBy = "student")
    private Fee fees; // One student can have One fee Record

    @OneToMany(mappedBy = "student")
    private List<Visitors> visitors;


    // Constructor that align with mapper
    public Student(Long id, String name, String gender, String address, String nationality,
                   LocalDate dateOfBirth, String contactNumber, String username, String parentContact,
                   String emergencyContact, String idProof,
                   String password, boolean isActive) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.username = username;
        this.parentContact = parentContact;
        this.emergencyContact = emergencyContact;
        this.idProof = idProof;
        this.password = password;
        this.isActive = isActive;
    }


//    public Student(Long id, String name, String gender, String address, String nationality, LocalDate dateOfBirth, String contactNumber, String email, String parentContact, String emergencyContact, String idProof, String profilePicture,Role role, String password, boolean isActive) {
//        this.id = id;
//        this.name = name;
//        this.gender = gender;
//        this.address = address;
//        this.nationality = nationality;
//        this.dateOfBirth = dateOfBirth;
//        this.contactNumber = contactNumber;
//        this.email = email;
//        this.parentContact = parentContact;
//        this.emergencyContact = emergencyContact;
//        this.idProof = idProof;
//        this.profilePicture = profilePicture;
//        this.role = role;
//        this.password = password;
//        this.isActive = isActive;
//    }
}

