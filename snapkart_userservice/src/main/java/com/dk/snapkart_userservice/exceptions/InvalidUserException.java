package com.dk.snapkart_userservice.exceptions;

public class InvalidUserException extends  RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
