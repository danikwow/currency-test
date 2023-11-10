package com.example.currencytest.adapter.jpa.adapter;

import com.example.currencytest.adapter.jpa.repository.TransactionJpaRepository;
import com.example.currencytest.app.Exception.TransactionNotFoundException;
import com.example.currencytest.app.repository.TransactionRepository;
import com.example.currencytest.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepository {
    private final TransactionJpaRepository repository;
    @Override
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionBetweenDate(Date startDate, Date endDate) {
        List<Transaction> listOfTransaction = repository.findAllByCreateDateBetween(startDate, endDate);
        if (listOfTransaction.isEmpty()) {
            throw new TransactionNotFoundException(startDate.toString() + " " + endDate.toString());
        }
        return listOfTransaction;
    }
}
