package com.codilien.hostelmanagementsystem.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

  private String resourceName;

  public ResourceNotFoundException(String resourceName) {
    super("No " + resourceName + " found with the given ID. Please check and try again.");
    this.resourceName = resourceName;
  }
}
