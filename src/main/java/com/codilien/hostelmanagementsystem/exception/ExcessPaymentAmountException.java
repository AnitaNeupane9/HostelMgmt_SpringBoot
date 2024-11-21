package com.codilien.hostelmanagementsystem.exception;

public class ExcessPaymentAmountException extends RuntimeException{

    public ExcessPaymentAmountException(String message) {
        super(message);
    }
}
