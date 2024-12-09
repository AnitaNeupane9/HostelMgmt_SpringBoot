package com.codilien.hostelmanagementsystem.service;

import com.codilien.hostelmanagementsystem.DTO.ReviewReplyDto;

import java.util.List;

public interface ReviewReplyService {
    ReviewReplyDto addReply(Long reviewId, ReviewReplyDto reviewReplyDto);
    List<ReviewReplyDto> getAllReplies(Long reviewId);
    ReviewReplyDto updateReply(Long reviewId, Long replyId, ReviewReplyDto reviewReplyDto);
    void deleteReply(Long reviewId, Long replyId);
}
