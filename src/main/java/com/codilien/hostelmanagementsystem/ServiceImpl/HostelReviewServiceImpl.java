package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.DTO.*;
import com.codilien.hostelmanagementsystem.exception.*;
import com.codilien.hostelmanagementsystem.model.*;
import com.codilien.hostelmanagementsystem.repository.*;
import com.codilien.hostelmanagementsystem.service.HostelReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HostelReviewServiceImpl implements HostelReviewService {

    private final HostelReviewRepository hostelReviewRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public HostelReviewServiceImpl(HostelReviewRepository hostelReviewRepository,
                                   StudentRepository studentRepository) {

        this.hostelReviewRepository = hostelReviewRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @PreAuthorize("hasRole('STUDENT')")
    public HostelReviewDto addReview(HostelReviewDto hostelReviewDto) {
        try {
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            Student student = studentRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new ResourceNotFoundException("Student"));

            if (student == null) {
                throw new ResourceNotFoundException("Student");
            }
            if (!student.isActive()) {
                throw new InactiveStudentException("Student must be active to post a review.");
            }

            // Create and save the review
            HostelReview hostelReview = new HostelReview();
            hostelReview.setRating(hostelReviewDto.getRating());
            hostelReview.setContent(hostelReviewDto.getContent());
            hostelReview.setStudent(student);

            HostelReview savedReview = hostelReviewRepository.save(hostelReview);
            return mapToReviewResponse(savedReview);

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("You've already reviewed this hostel.");
        }
    }
    
    @Override
    public HostelReviewDto getReview(Long id) {
        HostelReview hostelReview = hostelReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review"));
        return mapToReviewResponse(hostelReview);
    }

    @Override
    public Page<HostelReviewDto> getAllReviews(Pageable pageable) {
        return hostelReviewRepository.findAll(pageable)
                .map(review -> mapToReviewResponse(review));
    }


    @Override
    @PreAuthorize("hasRole('STUDENT') and @securityService.isOwner(#id, 'REVIEW')")
    public HostelReviewDto updateReview(Long id, HostelReviewDto hostelReviewDto) {
        HostelReview review = hostelReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review"));

        // Update review details
        review.setRating(hostelReviewDto.getRating());
        review.setContent(hostelReviewDto.getContent());

        HostelReview updatedReview = hostelReviewRepository.save(review);
        return mapToReviewResponse(updatedReview);
    }

    @Override
    @PreAuthorize("hasRole('STUDENT') and @securityService.isOwner(#id, 'REVIEW') or (hasRole('ADMIN'))" )
    public void deleteReview(Long id) {
        HostelReview review = hostelReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review"));
        hostelReviewRepository.delete(review);
    }


    @Override
    public HostelReview addLikes(Long reviewId) {
        Student student = getLoggedInStudent();
        return updateLikesOrDislikes(reviewId, student.getId(), true);
    }

    @Override
    public HostelReview addDislikes(Long reviewId) {
        Student student = getLoggedInStudent();
        return updateLikesOrDislikes(reviewId, student.getId(), false);
    }

    private HostelReview updateLikesOrDislikes(Long reviewId, Long studentId, boolean isLike) {
        // Fetch the review; throw exception if not found
        HostelReview review = hostelReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review"));

        Set<Long> likedBy = review.getLikedByStudents();
        Set<Long> dislikedBy = review.getDislikedByStudents();

        if (isLike) {
            if (likedBy.contains(studentId)) {
                // Unlike: If the student already liked the review, remove their like
                likedBy.remove(studentId);
                review.setLikes(review.getLikes() - 1);
            } else {
                // Like: Add the student to the liked set and increment like count
                likedBy.add(studentId);
                review.setLikes(review.getLikes() + 1);

                // Ensure mutual exclusivity: Remove dislike if it exists
                if (dislikedBy.remove(studentId)) {
                    review.setDislikes(Math.max(0, review.getDislikes() - 1));
                }
            }
        } else {
            if (dislikedBy.contains(studentId)) {
                // Remove dislike if the student already disliked the review
                dislikedBy.remove(studentId);
                review.setDislikes(review.getDislikes() - 1);
            } else {
                // Add dislike: Add the student to the disliked set and increment dislike count
                dislikedBy.add(studentId);
                review.setDislikes(review.getDislikes() + 1);

                // Ensuring mutual exclusivity (Remove like if it exists)
                if (likedBy.remove(studentId)) {
                    review.setLikes(Math.max(0, review.getLikes() - 1));
                }
            }
        }
        return hostelReviewRepository.save(review);
    }

    private Student getLoggedInStudent() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return studentRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Student"));
    }


    public HostelReviewDto mapToReviewResponse(HostelReview review) {
        HostelReviewDto response = new HostelReviewDto();
        response.setId(review.getId());
        response.setRating(review.getRating());
        response.setContent(review.getContent());
        response.setLikes(review.getLikes());
        response.setDislikes(review.getDislikes());
        response.setStudentName(review.getStudent().getName());
        response.setReplies(review.getReplies().stream().map(reply -> {
            ReviewReplyDto replyDto = new ReviewReplyDto();
            replyDto.setId(reply.getId());
            replyDto.setContent(reply.getContent());
            replyDto.setStudentName(reply.getStudent().getName());
            return replyDto;
        }).collect(Collectors.toList()));
        return response;
    }
}
