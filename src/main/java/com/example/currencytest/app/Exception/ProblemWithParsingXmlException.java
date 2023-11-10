package com.example.currencytest.app.Exception;

public class ProblemWithParsingXmlException extends RuntimeException{
    private final static String EXCEPTION_MESSAGE = "Problem with parsing xml from CB: ";
    public ProblemWithParsingXmlException(String message) {
        super(EXCEPTION_MESSAGE + message);
    }
}
