package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {

    @Query("SELECT f FROM Fee f WHERE f.dueDate < :currentDate AND f.remainingAmount > :minRemainingAmount")
    List<Fee> findOverdue(@Param("currentDate") LocalDate currentDate, @Param("minRemainingAmount") double minRemainingAmount);

//    List<Fee> findOverdue(LocalDate dueDate, double remainingAmount);

}
