package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository  extends JpaRepository<Complaint, Long> {
}
