package com.codilien.hostelmanagementsystem.model;

import com.codilien.hostelmanagementsystem.Auditing.BaseEntity;
import com.codilien.hostelmanagementsystem.Enum.FeeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class Fee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private Double totalAmount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    @Min(0)
    private Double penaltyFee;
    @Min(0)
    private Double netAmount;
    private Double remainingAmount;

//    @Enumerated(EnumType.STRING)
//    private FeeType feeType;

    @OneToOne
    @JoinColumn(name = "studentId")
    private Student student;        // One fee record is associated with one student

    @OneToMany(mappedBy = "fee")
    private List<Payment> payments;

}
