package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.Request.VisitorRequest;
import com.codilien.hostelmanagementsystem.Response.BaseResponse;
import com.codilien.hostelmanagementsystem.Response.BaseResponseWithList;
import com.codilien.hostelmanagementsystem.Response.VisitorsResponse;
import com.codilien.hostelmanagementsystem.constants.ApiPaths;
import com.codilien.hostelmanagementsystem.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
//@CrossOrigin(origins = "http://localhost:4209")
@RequestMapping(ApiPaths.BASE_VISITOR_API)
public class VisitorsController {

    private VisitorService visitorService;

    @Autowired
    public VisitorsController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping(ApiPaths.CREATE_VISITOR)
    public ResponseEntity<BaseResponse> createVisitor(@RequestBody VisitorRequest request) {
        BaseResponse response = visitorService.addVisitor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping(ApiPaths.GET_VISITOR_BY_ID)
    public ResponseEntity<BaseResponse> getVisitor(@PathVariable Long id) {
        BaseResponse response = visitorService.getVisitorById(id);
        return ResponseEntity.ok(response);
    }

//    @GetMapping({"/",""})
//    public ResponseEntity<List<BaseResponse>> getAllVisitors() {
//        List<BaseResponse> response = visitorService.getAllVisitor();
//        return ResponseEntity.ok(response);
//    }

    @GetMapping(ApiPaths.GET_ALL_VISITORS)
    public ResponseEntity<BaseResponseWithList<VisitorsResponse>> getAllVisitors() {
        BaseResponseWithList<VisitorsResponse> response = visitorService.getAllVisitor();
        return ResponseEntity.ok(response);
    }

    @PutMapping(ApiPaths.UPDATE_VISITOR)
    public ResponseEntity<BaseResponse> updateVisitor(@PathVariable Long id, @RequestBody VisitorRequest request){
        BaseResponse response = visitorService.updateVisitor(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(ApiPaths.DELETE_VISITOR)
    public ResponseEntity<BaseResponse> deleteVisitor(@PathVariable Long id){
        BaseResponse response = visitorService.deleteVisitor(id);
        return ResponseEntity.ok(response);
    }

}
