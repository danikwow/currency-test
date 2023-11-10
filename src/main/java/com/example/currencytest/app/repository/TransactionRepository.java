package com.example.currencytest.app.repository;

import com.example.currencytest.domain.Transaction;
import com.example.currencytest.domain.enumration.CharCode;

import java.util.Date;
import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    List<Transaction> getTransactionBetweenDate(Date startDate, Date endDate);
}
