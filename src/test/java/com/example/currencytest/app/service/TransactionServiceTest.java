package com.example.currencytest.app.service;

import com.example.currencytest.adapter.entity_factory.CurrencyRateFactory;
import com.example.currencytest.adapter.entity_factory.TransactionFactory;
import com.example.currencytest.app.repository.TransactionRepository;
import com.example.currencytest.app.service.impl.TransactionServiceImpl;
import com.example.currencytest.domain.CurrencyRate;
import com.example.currencytest.domain.Transaction;
import com.example.currencytest.domain.enumration.CharCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private CurrencyRateService rateService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void sepUp() {
        openMocks(this);
    }

    @Test
    void testCreateTransaction() {
        Transaction transaction = TransactionFactory.FIRST_TRANSACTION;
        when(repository.save(transaction)).thenReturn(transaction);

        Transaction savedTransaction = transactionService.createTransaction(transaction);

        assertNotNull(savedTransaction);
        assertEquals(transaction, savedTransaction);
    }

    @Test
    void testGetReportBetweenDate_RUB() {
        Date startDate = Date.from(Instant.now().minusSeconds(100));
        Date endDate = Date.from(Instant.now());
        CharCode code = CharCode.RUB;
        List<Transaction> transactions = List.of(TransactionFactory.SECOND_TRANSACTION,
                TransactionFactory.SECOND_TRANSACTION);

        when(repository.getTransactionBetweenDate(startDate, endDate)).thenReturn(transactions);

        List<Transaction> result = transactionService.getReportBetweenDate(startDate, endDate, code);

        assertNotNull(result);
        assertEquals(transactions.size(), result.size());
    }

    @Test
    void testGetReportBetweenDate_NonRUB() {
        Date startDate = Date.from(Instant.now().minusSeconds(100));
        Date endDate = Date.from(Instant.now());
        CharCode code = CharCode.EUR;
        List<Transaction> transactions = List.of(TransactionFactory.FIRST_TRANSACTION,
                TransactionFactory.SECOND_TRANSACTION);
        CurrencyRate currencyRate = CurrencyRateFactory.FIRST_CURRENCY_RATE;

        when(repository.getTransactionBetweenDate(startDate, endDate)).thenReturn(transactions);
        when(rateService.getActualCurrencyRateByCode(code)).thenReturn(Optional.of(currencyRate));

        List<Transaction> result = transactionService.getReportBetweenDate(startDate, endDate, code);

        assertNotNull(result);
        assertEquals(transactions.size(), result.size());

        List<BigDecimal> expectedValue = List.of(BigDecimal.valueOf(5000).divide(BigDecimal.valueOf(90), 4, RoundingMode.HALF_EVEN),
                BigDecimal.valueOf(10000).divide(BigDecimal.valueOf(90), 4, RoundingMode.HALF_EVEN));
        for (int i = 0; i < result.size(); i++) {
            assertEquals(expectedValue.get(i), result.get(i).getAmount());
        }
    }
}