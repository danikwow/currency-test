package com.example.currencytest.adapter.facade;

import com.example.currencytest.adapter.dto.response.TransactionDto;
import com.example.currencytest.adapter.entity_factory.TransactionDtoFactory;
import com.example.currencytest.adapter.entity_factory.TransactionFactory;
import com.example.currencytest.adapter.mapper.TransactionMapper;
import com.example.currencytest.app.Exception.IncorrectDateException;
import com.example.currencytest.app.Exception.ToLongDescriptionException;
import com.example.currencytest.app.service.TransactionService;
import com.example.currencytest.domain.Transaction;
import com.example.currencytest.domain.enumration.CharCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TransactionFacadeTest {

    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private TransactionFacade transactionFacade;

    private final static String TO_LONG_DESCRIPTION = """
            Sun of the sleepless! Melancholy star!
            Whose tearful beam glows tremulously far,
            That show’st the darkness thou canst not dispel,
            How like art thou to joy remember’d well!
                        
            So gleams the past, the light of other days,
            Which shines, but warms not with its powerless rays;
            A night-beam Sorrow watch eth to behold,
            Distinct, but distant — clear, but oh, how cold!\s
                        
            """;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testCreateNewTransaction_ValidDescription() {
        TransactionDto validTransactionDto = TransactionDtoFactory.FIRST_TRANSACTION;
        when(transactionService.createTransaction(any())).thenReturn(TransactionFactory.FIRST_TRANSACTION);
        when(transactionMapper.toTransactionDto(any())).thenReturn(TransactionDtoFactory.FIRST_TRANSACTION);

        TransactionDto result = transactionFacade.createNewTransaction(validTransactionDto);

        assertNotNull(result);
        assertEquals(validTransactionDto, result);
        verify(transactionService).createTransaction(any());
        verify(transactionMapper).toTransactionDto(any());
    }

    @Test
    void estCreateNewTransaction_InvalidDescription() {
        TransactionDto invalidTransactionDto = new TransactionDto(Date.from(Instant.now()), TO_LONG_DESCRIPTION, BigDecimal.valueOf(500000));

        assertThrows(ToLongDescriptionException.class, () -> transactionFacade.createNewTransaction(invalidTransactionDto));
        verifyNoInteractions(transactionService, transactionMapper);
    }

    @Test
    void testGetReportBetweenDate_ValidDates() {
        Date startDate = Date.from(Instant.now().minusSeconds(1000)) ;
        Date endDate = Date.from(Instant.now());
        List<Transaction> exchangeTransaction = List.of(TransactionFactory.FIRST_TRANSACTION,
                TransactionFactory.SECOND_TRANSACTION);

        List<TransactionDto> exchangeTransactionDto = List.of(TransactionDtoFactory.FIRST_TRANSACTION,
                TransactionDtoFactory.SECOND_TRANSACTION);

        when(transactionService.getReportBetweenDate(startDate, endDate, CharCode.EUR))
                .thenReturn(exchangeTransaction);

        when(transactionMapper.toTransactionDto(any()))
                .thenReturn(exchangeTransactionDto.get(0))
                .thenReturn(exchangeTransactionDto.get(1));

        List<TransactionDto> result = transactionFacade.getReportBetweenDate(startDate, endDate, CharCode.EUR);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionService).getReportBetweenDate(startDate, endDate, CharCode.EUR);
        verify(transactionMapper, times(2)).toTransactionDto(any());
    }

    @Test
    void testGetReportBetweenDate_InvalidDates() {
        Date startDate = Date.from(Instant.now());
        Date endDate = Date.from(Instant.now().minusSeconds(1000));

        assertThrows(IncorrectDateException.class, () -> transactionFacade.getReportBetweenDate(startDate, endDate, CharCode.EUR));
        verifyNoInteractions(transactionService, transactionMapper);
    }
}