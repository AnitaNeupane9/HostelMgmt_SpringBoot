package com.codilien.hostelmanagementsystem.exception;

import com.codilien.hostelmanagementsystem.Response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle the ResourceNotFoundException and return a custom error message with a 404 status
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // Handle ResourceConflictException
    @ExceptionHandler(ResourceConflictException.class)
    public  ResponseEntity<String> handleUsernameALreadyExists(ResourceConflictException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    //Handle InactiveStudentException
    @ExceptionHandler(InactiveStudentException.class)
    public ResponseEntity<String> handleInactiveStudent(InactiveStudentException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RoomUnavailableException.class)
    public ResponseEntity<ExceptionResponse> handleRoomUnavailableException(RoomUnavailableException ex, WebRequest request) {
        // Get current timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Prepare the exception response
        ExceptionResponse response = new ExceptionResponse(
                timestamp,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ExcessPaymentAmountException.class)
    public ResponseEntity<String> handleExcessPaymentAmount(ExcessPaymentAmountException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RoomFullException.class)
    public ResponseEntity<String> handleRoomFull(RoomFullException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<BaseResponse> handleApplicationException(ApplicationException ex){
        BaseResponse response = new BaseResponse();
        response.setCode(404);
        response.setMessage(ex.getMessage());
        response.setStatus(false);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    // handlers for different exceptions if needed
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleGenericException(Exception ex) {
        BaseResponse response = new BaseResponse();
        response.setStatus(false);
//        response.setMessage("An Unexpected Error Occurred.");
        response.setMessage(ex.getMessage());
        response.setCode(400);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

