package com.example.currencytest.app.Exception;

public class TransactionNotFoundException  extends RuntimeException{
    private final static String EXCEPTION_MESSAGE = "Transaction between date, not found: ";
    public TransactionNotFoundException(String message) {
        super(EXCEPTION_MESSAGE + message);
    }
}
