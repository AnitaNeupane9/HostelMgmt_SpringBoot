package com.codilien.hostelmanagementsystem.DTO;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HostelReviewDto {
    private Long id;
    private int rating;
    private String content;
    private int likes;
    private int dislikes;
    private String studentName;
//    private Long studentId;
    private List<ReviewReplyDto> replies;
}
