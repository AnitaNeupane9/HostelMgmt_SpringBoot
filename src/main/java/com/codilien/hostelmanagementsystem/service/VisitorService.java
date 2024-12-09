package com.codilien.hostelmanagementsystem.service;

import com.codilien.hostelmanagementsystem.Request.VisitorRequest;
import com.codilien.hostelmanagementsystem.Response.BaseResponse;
import com.codilien.hostelmanagementsystem.Response.BaseResponseWithList;
import com.codilien.hostelmanagementsystem.Response.DetailedVisitorResponse;
import com.codilien.hostelmanagementsystem.Response.VisitorsResponse;

import java.util.List;


public interface VisitorService {
    public VisitorsResponse addVisitor(VisitorRequest request);
    public BaseResponse<DetailedVisitorResponse> getVisitorById(Long id);
    public BaseResponseWithList<VisitorsResponse> getAllVisitor();

//    public List<VisitorsResponse> getALlVisitor();
    public VisitorsResponse updateVisitor(Long id, VisitorRequest request);
    public BaseResponse deleteVisitor(Long id);
}
