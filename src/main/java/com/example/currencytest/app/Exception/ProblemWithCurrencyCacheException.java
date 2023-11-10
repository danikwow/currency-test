package com.example.currencytest.app.Exception;

public class ProblemWithCurrencyCacheException  extends RuntimeException{
    private final static String EXCEPTION_MESSAGE = "Problem with currency cache: ";
    public ProblemWithCurrencyCacheException(String message) {
        super(EXCEPTION_MESSAGE + message);
    }
}
