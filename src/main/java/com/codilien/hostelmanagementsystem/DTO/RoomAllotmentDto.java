package com.codilien.hostelmanagementsystem.DTO;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAllotmentDto {
    private Long id;

    @PastOrPresent(message = "Allotment cannot be in the future.")
    private LocalDate allotmentDate;

    private String duration;

    @PastOrPresent(message = "CheckIn date cannot be in the future.")
    private LocalDateTime checkIn;

    @PastOrPresent(message = "Checkout date cannot be in the future.")
    private LocalDateTime checkOut;
    private Long studentId;
    private Long roomId;
}
