package com.codilien.hostelmanagementsystem.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitorsResponse  extends BaseResponse{
    private Long visitorId;
    private String visitorName;
    private String visitorPurpose;
}
