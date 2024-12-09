package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.HostelReviewDto;
import com.codilien.hostelmanagementsystem.ServiceImpl.HostelReviewServiceImpl;
import com.codilien.hostelmanagementsystem.model.HostelReview;
import com.codilien.hostelmanagementsystem.service.HostelReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class HostelReviewController {

    private HostelReviewService hostelReviewService;
    private HostelReviewServiceImpl service;

    @Autowired
    public HostelReviewController(HostelReviewService hostelReviewService) {
        this.hostelReviewService = hostelReviewService;
    }


    @PostMapping("/add")
    public ResponseEntity<HostelReviewDto> addReview(@RequestBody HostelReviewDto hostelReviewDto) {
        HostelReviewDto createdReview = hostelReviewService.addReview(hostelReviewDto);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HostelReviewDto> getReview(@PathVariable Long id) {
        HostelReviewDto review = hostelReviewService.getReview(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<HostelReviewDto>> getAllReviews(Pageable pageable) {
        Page<HostelReviewDto> reviews = hostelReviewService.getAllReviews(pageable);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<HostelReviewDto> updateReview(
            @PathVariable Long id,
            @RequestBody HostelReviewDto hostelReviewDto) {
        HostelReviewDto updatedReview = hostelReviewService.updateReview(id, hostelReviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        hostelReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{reviewId}/like")
    public ResponseEntity<HostelReviewDto> likeReview(@PathVariable Long reviewId) {
        HostelReview updatedReview = hostelReviewService.addLikes(reviewId);
        HostelReviewDto responseDto = hostelReviewService.mapToReviewResponse(updatedReview);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/{reviewId}/dislike")
    public ResponseEntity<HostelReviewDto> dislikeReview(@PathVariable Long reviewId) {
        HostelReview updatedReview = hostelReviewService.addDislikes(reviewId);
        HostelReviewDto responseDto = hostelReviewService.mapToReviewResponse(updatedReview);
        return ResponseEntity.ok(responseDto);
    }
}
