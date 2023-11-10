package com.example.currencytest.adapter.rest.controller;

import com.example.currencytest.adapter.rest.client.CentralBankClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CentralBankClient centralBankClient;

    @Test
    void testUpdateCurrencyRate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/currency-rate"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(centralBankClient).getAndSaveCurrencyFromCbBank();
    }
}