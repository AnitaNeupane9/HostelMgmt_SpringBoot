package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.DTO.ReviewReplyDto;
import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.model.*;
import com.codilien.hostelmanagementsystem.repository.ReviewReplyRepository;
import com.codilien.hostelmanagementsystem.repository.HostelReviewRepository;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import com.codilien.hostelmanagementsystem.service.ReviewReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewReplyServiceImpl implements ReviewReplyService {

    private final ReviewReplyRepository reviewReplyRepository;
    private final HostelReviewRepository hostelReviewRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ReviewReplyServiceImpl(
            ReviewReplyRepository reviewReplyRepository,
            HostelReviewRepository hostelReviewRepository,
            StudentRepository studentRepository) {

        this.reviewReplyRepository = reviewReplyRepository;
        this.hostelReviewRepository = hostelReviewRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    @PreAuthorize("hasRole('STUDENT')")
    public ReviewReplyDto addReply(Long reviewId, ReviewReplyDto reviewReplyDto) {
        HostelReview hostelReview = hostelReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("HostelReview"));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Student"));

        ReviewReply reviewReply = new ReviewReply();
        reviewReply.setContent(reviewReplyDto.getContent());
        reviewReply.setHostelReview(hostelReview);
        reviewReply.setStudent(student);

        ReviewReply savedReply = reviewReplyRepository.save(reviewReply);
        return mapToDto(savedReply);
    }


    @Override
    public List<ReviewReplyDto> getAllReplies(Long reviewId) {
        HostelReview hostelReview = hostelReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("HostelReview"));

        List<ReviewReply> replies = reviewReplyRepository.findByHostelReview(hostelReview);
        return replies.stream()
                .map(reply -> mapToDto(reply))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('STUDENT') and @securityService.isOwner(#replyId, 'REPLY')")
    public ReviewReplyDto updateReply(Long reviewId, Long replyId, ReviewReplyDto reviewReplyDto) {
        ReviewReply reviewReply = reviewReplyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("Reply"));

        // Check if the reply belongs to the correct review
        if (!reviewReply.getHostelReview().getId().equals(reviewId)) {
            throw new ResourceNotFoundException("Reply");
        }

        reviewReply.setContent(reviewReplyDto.getContent());

        ReviewReply updatedReply = reviewReplyRepository.save(reviewReply);
        return mapToDto(updatedReply);
    }


    @Override
    @PreAuthorize("hasRole('STUDENT') and @securityService.isOwner(#replyId, 'REPLY') or (hasRole('ADMIN'))")
    public void deleteReply(Long reviewId, Long replyId) {
        ReviewReply reviewReply = reviewReplyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewReply"));

//// checking Owner
//        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//        Student student =  studentRepository.findByUsername(loggedInUsername)
//                .orElseThrow(() -> new ResourceNotFoundException("Student"));
//
//        if (!reviewReply.getStudent().getId().equals(student.getId())){
//            throw new AccessDeniedException("Access is denied.");
//        }

        // Check if the reply belongs to the correct review
        if (!reviewReply.getHostelReview().getId().equals(reviewId)) {
            throw new ResourceNotFoundException("ReviewReply");
        }

        reviewReplyRepository.delete(reviewReply);
    }


    private ReviewReplyDto mapToDto(ReviewReply reviewReply) {
        ReviewReplyDto dto = new ReviewReplyDto();
        dto.setId(reviewReply.getId());
        dto.setContent(reviewReply.getContent());
        dto.setStudentId(reviewReply.getStudent().getId());
        dto.setStudentName(reviewReply.getStudent().getName());
        return dto;
    }
}
