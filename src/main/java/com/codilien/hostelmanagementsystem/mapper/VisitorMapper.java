package com.codilien.hostelmanagementsystem.mapper;

import com.codilien.hostelmanagementsystem.Request.VisitorRequest;
import com.codilien.hostelmanagementsystem.Response.DetailedVisitorResponse;
import com.codilien.hostelmanagementsystem.model.Student;
import com.codilien.hostelmanagementsystem.model.Visitors;
import com.codilien.hostelmanagementsystem.Response.VisitorsResponse;

public class VisitorMapper {

    // Map VisitorRequest to Visitors entity
    public static Visitors mapRequestToEntity(VisitorRequest request) {
        Visitors visitor = new Visitors();
        visitor.setName(request.getVisitorName());
        visitor.setCheckedIn(request.getVisitStart());
        visitor.setCheckedOut(request.getVisitEnd());
        visitor.setPurposeOfVisit(request.getVisitPurpose());
        visitor.setContactInfo(request.getVisitorContact());

        // Map studentId to a Student entity
        if (request.getAssociatedStudentId() != null){
            Student student = new Student();
            student.setId(request.getAssociatedStudentId());
            visitor.setStudent(student);
        }
        return visitor;
    }

    // Map Visitors entity to VisitorResponse
    public static VisitorsResponse mapEntityToResponse(Visitors visitor) {
        VisitorsResponse response = new VisitorsResponse();
        response.setVisitorId(visitor.getId());
        response.setVisitorName(visitor.getName());
        response.setVisitorPurpose(visitor.getPurposeOfVisit());
        return response;
    }


    // Map Visitors to DetailedVisitorResponse
    public static DetailedVisitorResponse mapToDetailedResponse(Visitors visitors){
        DetailedVisitorResponse response = new DetailedVisitorResponse();
        response.setVisitorId(visitors.getId());
        response.setVisitorName(visitors.getName());
        response.setVisitStart(visitors.getCheckedIn());
        response.setVisitEnd(visitors.getCheckedOut());
        response.setVisitorPurpose(visitors.getPurposeOfVisit());
        response.setVisitorContact(visitors.getContactInfo());
        response.setAssociatedStudentId(visitors.getStudent().getId());

        return response;
    }



//    // Convert Visitors entity to VisitorRequest DTO (for updates)
//    public static VisitorRequest mapEntityToRequest(Visitors visitor) {
//        VisitorRequest request = new VisitorRequest();
//        request.setVisitorName(visitor.getName());
//        request.setVisitStart(visitor.getCheckedIn());
//        request.setVisitEnd(visitor.getCheckedOut());
//        request.setVisitPurpose(visitor.getPurposeOfVisit());
//        request.setVisitorContact(visitor.getContactInfo());
//        request.setAssociatedStudentId(visitor.getStudent() != null ? visitor.getStudent().getId() : null);
//        return request;
//    }
}
