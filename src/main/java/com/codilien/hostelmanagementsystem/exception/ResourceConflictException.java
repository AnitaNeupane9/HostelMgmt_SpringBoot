package com.codilien.hostelmanagementsystem.exception;


public class ResourceConflictException extends RuntimeException {

    private String resourceName;
    public ResourceConflictException(String resourceName) {
        super(resourceName + " is already exists. Please choose different one.");
    }
}
