package com.example.currencytest.adapter.facade;

import com.example.currencytest.adapter.dto.response.TransactionDto;
import com.example.currencytest.adapter.mapper.TransactionMapper;
import com.example.currencytest.app.Exception.IncorrectDateException;
import com.example.currencytest.app.Exception.ToLongDescriptionException;
import com.example.currencytest.app.service.TransactionService;
import com.example.currencytest.domain.Transaction;
import com.example.currencytest.domain.enumration.CharCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionFacade {
    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;
    public TransactionDto createNewTransaction(TransactionDto transactionDto) {
        if (transactionDto.getDescription().length() >= 255) {
            throw new ToLongDescriptionException(transactionDto.getDescription());
        }
        Transaction transaction = transactionService.createTransaction(transactionMapper.dtoToTransaction(transactionDto));
        return transactionMapper.toTransactionDto(transaction);
    }

    public List<TransactionDto> getReportBetweenDate(Date startDate, Date endDate, CharCode code) {
        if (endDate.before(startDate)) {
            throw new IncorrectDateException(startDate + " " + endDate);
        }
        return transactionService.getReportBetweenDate(startDate, endDate, code)
                .stream().map(transactionMapper::toTransactionDto)
                .toList();
    }
}
