package com.bdocg.service;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("The requested resource can not be found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}

