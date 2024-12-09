package com.codilien.hostelmanagementsystem.service;

import com.codilien.hostelmanagementsystem.DTO.HostelReviewDto;
import com.codilien.hostelmanagementsystem.model.HostelReview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HostelReviewService {
    HostelReviewDto addReview(HostelReviewDto hostelReviewDto);
    HostelReviewDto getReview(Long id);
    Page<HostelReviewDto> getAllReviews(Pageable pageable);
    HostelReviewDto updateReview(Long id, HostelReviewDto hostelReviewDto);
    void deleteReview(Long id);
    HostelReview addLikes(Long reviewId);
    HostelReview addDislikes(Long reviewId);
    HostelReviewDto mapToReviewResponse(HostelReview hostelReview);
}
