package com.codilien.hostelmanagementsystem.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long id;

    @Min(0)
    private double paymentAmount;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;
    private String paymentMethod;

    private String remarks;
//    private Long studentId;
    private Long feeId;
}
