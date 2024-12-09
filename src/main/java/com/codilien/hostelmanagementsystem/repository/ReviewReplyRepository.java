package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.ReviewReply;
import com.codilien.hostelmanagementsystem.model.HostelReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {
    List<ReviewReply> findByHostelReview(HostelReview hostelReview);
}
