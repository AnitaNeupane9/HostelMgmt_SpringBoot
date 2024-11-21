package com.codilien.hostelmanagementsystem.exception;

public class RoomFullException extends RuntimeException{

    public RoomFullException(String roomNumber) {
        super("Room with Room Number " + roomNumber + " is full. No available slots left.");
    }
}
