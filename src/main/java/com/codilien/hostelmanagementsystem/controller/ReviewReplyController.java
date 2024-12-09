package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.ReviewReplyDto;
import com.codilien.hostelmanagementsystem.model.ReviewReply;
import com.codilien.hostelmanagementsystem.service.ReviewReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews/{reviewId}/replies")
public class ReviewReplyController {

    private ReviewReplyService reviewReplyService;

    @Autowired
    public ReviewReplyController(ReviewReplyService reviewReplyService) {
        this.reviewReplyService = reviewReplyService;
    }

    @PostMapping("/reply")
    public ResponseEntity<ReviewReplyDto> addReply(
            @PathVariable Long reviewId,
            @RequestBody ReviewReplyDto reviewReplyDto) {
        ReviewReplyDto createdReply = reviewReplyService.addReply(reviewId, reviewReplyDto);
        return new ResponseEntity<>(createdReply, HttpStatus.CREATED);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<?> getReplies(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewReplyService.getAllReplies(reviewId));
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<ReviewReplyDto> updateReply(
            @PathVariable Long reviewId,
            @PathVariable Long replyId,
            @RequestBody ReviewReplyDto reviewReplyDto) {
        ReviewReplyDto updatedReply = reviewReplyService.updateReply(reviewId, replyId, reviewReplyDto);
        return ResponseEntity.ok(updatedReply);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long reviewId, @PathVariable Long replyId) {
        reviewReplyService.deleteReply(reviewId, replyId);
        return ResponseEntity.ok("Reply deleted Successfully.");
    }
}
