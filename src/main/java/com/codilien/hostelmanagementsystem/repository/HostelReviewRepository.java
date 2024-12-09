package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.HostelReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelReviewRepository extends JpaRepository<HostelReview, Long> {
    boolean existsById(Long id);
}
