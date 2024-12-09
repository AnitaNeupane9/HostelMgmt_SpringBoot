package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.Request.VisitorRequest;
import com.codilien.hostelmanagementsystem.Response.BaseResponse;
import com.codilien.hostelmanagementsystem.Response.BaseResponseWithList;
import com.codilien.hostelmanagementsystem.Response.DetailedVisitorResponse;
import com.codilien.hostelmanagementsystem.Response.VisitorsResponse;
import com.codilien.hostelmanagementsystem.exception.ApplicationException;
import com.codilien.hostelmanagementsystem.exception.InactiveStudentException;
import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.mapper.StudentMapper;
import com.codilien.hostelmanagementsystem.mapper.VisitorMapper;
import com.codilien.hostelmanagementsystem.model.Student;
import com.codilien.hostelmanagementsystem.model.Visitors;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import com.codilien.hostelmanagementsystem.repository.VisitorsRepository;
import com.codilien.hostelmanagementsystem.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitorServiceImpl implements VisitorService {

    private VisitorsRepository visitorsRepository;
    private StudentRepository studentRepository;

    @Autowired
    public VisitorServiceImpl(VisitorsRepository visitorsRepository, StudentRepository studentRepository) {
        this.visitorsRepository = visitorsRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public VisitorsResponse addVisitor(VisitorRequest request) {
            Visitors visitor = VisitorMapper.mapRequestToEntity(request);
        Optional<Student> studentOpt = studentRepository.findById(request.getAssociatedStudentId());
        if (studentOpt.isEmpty()) {
            throw new ApplicationException("No Visitor found with the given ID. Please check and try again.");
        }

        Student student = studentOpt.get();
        // Check if the Student status is Active
        if (!student.isActive()) {
            throw new ApplicationException("Visitor record cannot be created for Inactive student.");
        }
            Visitors savedVisitor = visitorsRepository.save(visitor);

            VisitorsResponse response = VisitorMapper.mapEntityToResponse(savedVisitor);
            response.setCode(200);
            response.setMessage("Visitors added successfully.");
            response.setStatus(true);
        return response;
    }

    @Override
    public BaseResponse<DetailedVisitorResponse> getVisitorById(Long id) {
        BaseResponse<DetailedVisitorResponse> response = new BaseResponse<>();
        try {
            Visitors visitor = visitorsRepository.findById(id)
                    .orElseThrow(() -> new ApplicationException("No Visitor found with the given ID. Please check and try again."));

            DetailedVisitorResponse detailedResponse = VisitorMapper.mapToDetailedResponse(visitor);
            response.setCode(200);
            response.setMessage("Visitor retrieved successfully");
            response.setStatus(true);
            response.setData(detailedResponse);
            return response;

        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public BaseResponseWithList<VisitorsResponse> getAllVisitor() {
        List<Visitors> visitors = visitorsRepository.findAll();

        // Map Visitors entity list to VisitorsResponse list
        List<VisitorsResponse> responseList = visitors.stream()
                .map(visitor -> VisitorMapper.mapEntityToResponse(visitor))
                .collect(Collectors.toList());

        // Wrapping list in BaseResponseWithList
        BaseResponseWithList<VisitorsResponse> response = new BaseResponseWithList<>();
        response.setCode(200);
        response.setMessage(responseList.isEmpty() ? "No Visitors Found!" : "Visitors retrieved successfully");
        response.setStatus(true);
        response.setData(responseList);

        return response;
    }



    @Override
    public VisitorsResponse updateVisitor(Long id, VisitorRequest request) {
        try {
            Visitors visitor = visitorsRepository.findById(id)
                    .orElseThrow(() -> new ApplicationException("No Visitor found with the given ID. Please check and try again."));

            visitor.setName(request.getVisitorName());
            visitor.setCheckedIn(request.getVisitStart());
            visitor.setCheckedOut(request.getVisitEnd());
            visitor.setPurposeOfVisit(request.getVisitPurpose());
            visitor.setContactInfo(request.getVisitorContact());
            Visitors updatedVisitor = visitorsRepository.save(visitor);

            VisitorsResponse response = VisitorMapper.mapEntityToResponse(updatedVisitor);
            response.setCode(200);
            response.setMessage("Visitors updated successfully.");
            response.setStatus(true);
            return response;

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public BaseResponse deleteVisitor(Long id) {
        try {
            Visitors visitor = visitorsRepository.findById(id)
                    .orElseThrow(() -> new ApplicationException("No Visitor found with the given ID. Please check and try again."));
           visitorsRepository.delete(visitor);

            BaseResponse response = VisitorMapper.mapEntityToResponse(visitor);
            response.setCode(200);
            response.setMessage("Visitors deleted successfully.");
            response.setStatus(true);
           return response;

        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
