package com.example.currencytest.adapter.entity_factory;

import com.example.currencytest.domain.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

public class TransactionFactory {
    public final static Transaction FIRST_TRANSACTION = new Transaction(1L, Date.from(Instant.now()), "exchange transaction", BigDecimal.valueOf(5000));
    public final static Transaction SECOND_TRANSACTION = new Transaction(2L, Date.from(Instant.now().minusSeconds(100)), "exchange transaction 2", BigDecimal.valueOf(10000));
}
