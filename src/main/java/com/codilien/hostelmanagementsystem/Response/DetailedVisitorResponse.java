package com.codilien.hostelmanagementsystem.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailedVisitorResponse{
    private Long visitorId;
    private String visitorName;
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
    private String visitorContact;
    private String visitorPurpose;
    private Long associatedStudentId;
}
