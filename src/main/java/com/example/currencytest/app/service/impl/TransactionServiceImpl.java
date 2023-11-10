package com.example.currencytest.app.service.impl;

import com.example.currencytest.app.Exception.ProblemWithCurrencyCacheException;
import com.example.currencytest.app.repository.TransactionRepository;
import com.example.currencytest.app.service.CurrencyRateService;
import com.example.currencytest.app.service.TransactionService;
import com.example.currencytest.domain.CurrencyRate;
import com.example.currencytest.domain.Transaction;
import com.example.currencytest.domain.enumration.CharCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;
    private final CurrencyRateService rateService;

    @Transactional
    @Override
    public Transaction createTransaction(Transaction transaction) {
        Transaction saveTransaction = repository.save(transaction);
        log.info("Transaction " + saveTransaction.toString() + "created");
        return saveTransaction;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> getReportBetweenDate(Date startDate, Date endDate, CharCode code) {
        List<Transaction> transactionBetweenDate = repository.getTransactionBetweenDate(startDate, endDate);
        if (code.equals(CharCode.RUB)) {
            return transactionBetweenDate;
        } else {
            Optional<CurrencyRate> currencyRate = rateService.getActualCurrencyRateByCode(code);
            if (currencyRate.isPresent()) {
                List<Transaction> collect = transactionBetweenDate.stream()
                        .peek(transaction -> transaction.setAmount(transaction.getAmount()
                                .divide(BigDecimal.valueOf(currencyRate.get().getRate()), 4, RoundingMode.HALF_EVEN)))
                        .toList();
                log.info("Transaction report of " + code + " created: " + collect);
                return collect;
            } else {
                throw new ProblemWithCurrencyCacheException(currencyRate.toString());
            }
        }
    }


}
