package com.example.currencytest.adapter.rest.controller;

import com.example.currencytest.adapter.dto.response.TransactionDto;
import com.example.currencytest.adapter.entity_factory.TransactionDtoFactory;
import com.example.currencytest.adapter.facade.TransactionFacade;
import com.example.currencytest.domain.enumration.CharCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionFacade facade;

    @Test
    void testCreateTransaction() throws Exception {
        String stringTransaction = TransactionDtoFactory.FIRST_TRANSACTION_AS_JSON_STRING;

        when(facade.createNewTransaction(any(TransactionDto.class))).thenReturn(TransactionDtoFactory.FIRST_TRANSACTION);

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringTransaction))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetReportBetweenDateAboutTransaction() throws Exception {

        List<TransactionDto> mockTransactionDtoList = List.of(TransactionDtoFactory.FIRST_TRANSACTION,
                TransactionDtoFactory.SECOND_TRANSACTION);

        when(facade.getReportBetweenDate(any(Date.class), any(Date.class), any(CharCode.class))).thenReturn(mockTransactionDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/currency-rate")
                        .param("startDate", "2023-11-09")
                        .param("endDate", "2023-11-10")
                        .param("code", "USD"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}