package com.example.currencytest.app.handler;

import com.example.currencytest.app.Exception.IncorrectDateException;
import com.example.currencytest.app.Exception.ProblemWithCurrencyCacheException;
import com.example.currencytest.app.Exception.ProblemWithParsingXmlException;
import com.example.currencytest.app.Exception.ToLongDescriptionException;
import com.example.currencytest.app.Exception.TransactionNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        log.error(ex.getMessage());
        log.trace(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleToLongDescriptionException(ToLongDescriptionException ex) {
        log.error(ex.getMessage());
        log.trace(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ex.getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleIncorrectDateException(IncorrectDateException ex) {
        log.error(ex.getMessage());
        log.trace(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleProblemWithParsingXmlException(ProblemWithParsingXmlException ex) {
        log.error(ex.getMessage());
        log.trace(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleXmlMapperException(ProblemWithCurrencyCacheException ex) {
        log.error(ex.getMessage());
        log.trace(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleThrowable(Throwable ex) {
        log.error(ex.getMessage());
        log.trace(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage()));
    }

    @Data
    @NoArgsConstructor
    public static class ExceptionResponse {

        private final Instant time = Instant.now();
        private String message;

        public ExceptionResponse(String message) {
            this.message = message;
        }
    }
}
