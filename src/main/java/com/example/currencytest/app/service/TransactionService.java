package com.example.currencytest.app.service;

import com.example.currencytest.domain.Transaction;
import com.example.currencytest.domain.enumration.CharCode;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction dtoToTransaction);

    List<Transaction> getReportBetweenDate(Date startDate, Date endDate, CharCode code);
}
