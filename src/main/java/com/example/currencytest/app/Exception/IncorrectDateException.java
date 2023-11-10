package com.example.currencytest.app.Exception;

public class IncorrectDateException extends RuntimeException{
    private final static String EXCEPTION_MESSAGE = "Incorrect date: ";
    public IncorrectDateException(String message) {
        super(EXCEPTION_MESSAGE + message);
    }
}
