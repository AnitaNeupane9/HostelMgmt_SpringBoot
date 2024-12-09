package com.codilien.hostelmanagementsystem.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;

    private String name;
    private String gender;
    private String address;
    private String nationality;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Pattern(
            regexp = "^\\+?[0-9]{1,3}\\s?[0-9]{6,14}$",
            message = "Invalid phone number format"
    )
    private String contactNumber;


    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required!")
    @Email(message = "Email should be valid.")
    private String username;

    @Pattern(
            regexp = "^\\+?[0-9]{1,3}\\s?[0-9]{6,14}$",
            message = "Invalid phone number format"
    )
    private String parentContact;

    @Pattern(
            regexp = "^\\+?[0-9]{1,3}\\s?[0-9]{6,14}$",
            message = "Invalid phone number format"
    )
    private String emergencyContact;

    private String idProof;

//    private String profilePicture;

    //    private MultipartFile profilePicture;
    // Field for the saved file path
//    private String profilePicturePath;
    //    private Role role;
    @NotBlank
    @NonNull
    private String password;
    private boolean isActive;


}
