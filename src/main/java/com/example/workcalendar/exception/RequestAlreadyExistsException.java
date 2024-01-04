package com.example.workcalendar.exception;

public class RequestAlreadyExistsException extends RuntimeException {
    public RequestAlreadyExistsException(String message) {
        super(message);
    }
}
