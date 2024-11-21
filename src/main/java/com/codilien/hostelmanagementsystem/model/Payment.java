package com.codilien.hostelmanagementsystem.model;

import com.codilien.hostelmanagementsystem.Auditing.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private double paymentAmount;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;
    private String paymentMethod;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;
}
