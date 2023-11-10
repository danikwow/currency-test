package com.example.currencytest.adapter.jpa.adapter;

import com.example.currencytest.adapter.entity_factory.TransactionFactory;
import com.example.currencytest.adapter.jpa.repository.TransactionJpaRepository;
import com.example.currencytest.app.Exception.TransactionNotFoundException;
import com.example.currencytest.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TransactionRepositoryAdapterTest {

    @InjectMocks
    private TransactionRepositoryAdapter transactionRepositoryAdapter;
    @Mock
    private TransactionJpaRepository repository;

    private final static String EXCEPTION_MESSAGE = "Transaction between date, not found: ";

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSave() {
        Transaction transaction = TransactionFactory.FIRST_TRANSACTION;

        when(repository.save(transaction)).thenReturn(transaction);

        Transaction savedTransaction = transactionRepositoryAdapter.save(transaction);

        assertNotNull(savedTransaction);
        assertEquals(transaction, savedTransaction);
        verify(repository).save(transaction);
    }

    @Test
    void testGetTransactionBetweenDate() {
        Date startDate = Date.from(Instant.now().minusSeconds(100));
        Date endDate = Date.from(Instant.now());

        List<Transaction> transactions = List.of(TransactionFactory.FIRST_TRANSACTION,
                TransactionFactory.SECOND_TRANSACTION);

        when(repository.findAllByCreateDateBetween(startDate, endDate)).thenReturn(transactions);

        List<Transaction> result = transactionRepositoryAdapter.getTransactionBetweenDate(startDate, endDate);

        assertNotNull(result);
        assertEquals(transactions.size(), result.size());
        verify(repository).findAllByCreateDateBetween(startDate, endDate);
    }

    @Test
    void testGetTransactionBetweenDate_NoTransactionsFound() {
        Date startDate = Date.from(Instant.now().minusSeconds(100));
        Date endDate = Date.from(Instant.now());

        when(repository.findAllByCreateDateBetween(startDate, endDate)).thenReturn(List.of());

        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> transactionRepositoryAdapter.getTransactionBetweenDate(startDate, endDate));

        assertEquals(EXCEPTION_MESSAGE + startDate + " " + endDate, exception.getMessage());
        verify(repository).findAllByCreateDateBetween(startDate, endDate);
    }
}