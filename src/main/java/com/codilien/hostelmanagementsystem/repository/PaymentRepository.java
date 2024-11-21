package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
