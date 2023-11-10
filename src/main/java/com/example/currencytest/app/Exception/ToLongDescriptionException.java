package com.example.currencytest.app.Exception;

public class ToLongDescriptionException  extends RuntimeException{
    private final static String EXCEPTION_MESSAGE = "Description is to long: ";
    public ToLongDescriptionException(String message) {
        super(EXCEPTION_MESSAGE + message);
    }
}
