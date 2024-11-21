package com.codilien.hostelmanagementsystem.model;

import com.codilien.hostelmanagementsystem.Auditing.BaseEntity;
import com.codilien.hostelmanagementsystem.Enum.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private String address;
    private String nationality;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Past
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9]{1,3}\\s?[0-9]{6,14}$", message = "Invalid phone number format")
    private String contactNumber;


    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required!")
    @Email(message = "Email should be valid.")
    private String username;

    @Pattern(regexp = "^\\+?[0-9]{1,3}\\s?[0-9]{6,14}$", message = "Invalid phone number format")
    private String parentContact;

    @Pattern(regexp = "^\\+?[0-9]{1,3}\\s?[0-9]{6,14}$", message = "Invalid phone number format")
    private String emergencyContact;

    private String idProof;
//    private String profilePicture;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActive;


    @OneToOne(mappedBy = "student")
    private RoomAllotment roomAllotment; // One student has one room allotment

    @OneToMany(mappedBy = "student")
    private List<Complaint> complaints; // One student can file multiple complaints

    @OneToOne(mappedBy = "student")
    private Fee fees; // One student can have One fee Record



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

