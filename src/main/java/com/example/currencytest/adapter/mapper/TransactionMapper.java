package com.example.currencytest.adapter.mapper;

import com.example.currencytest.adapter.dto.response.TransactionDto;
import com.example.currencytest.domain.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction dtoToTransaction(TransactionDto transactionDto);

    TransactionDto toTransactionDto(Transaction transaction);
}
