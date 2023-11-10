package com.example.currencytest.adapter.entity_factory;

import com.example.currencytest.adapter.dto.response.TransactionDto;
import com.example.currencytest.domain.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

public class TransactionDtoFactory {
    public final static TransactionDto FIRST_TRANSACTION = new TransactionDto(Date.from(Instant.now()), "exchange transaction", BigDecimal.valueOf(5000));
    public final static TransactionDto SECOND_TRANSACTION = new TransactionDto(Date.from(Instant.now().minusSeconds(100)), "exchange transaction 2", BigDecimal.valueOf(10000));
    public final static String FIRST_TRANSACTION_AS_JSON_STRING = "{ \"createDate\": \"2023-11-10\", \"description\": \"Payment for services\", \"amount\": 250.5 }";
    public final static String SECOND_TRANSACTION_AS_JSON_STRING = "{ \"createDate\": \"2023-11-09\", \"description\": \"Product purchase\", \"amount\": 125.75 }";
}
