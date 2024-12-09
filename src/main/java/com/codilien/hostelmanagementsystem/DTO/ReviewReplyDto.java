package com.codilien.hostelmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReplyDto {

    private Long id;
    private String content;
    private Long studentId;
    private String studentName;

}
