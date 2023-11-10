package com.example.currencytest.app.service;

import com.example.currencytest.adapter.entity_factory.CurrencyRateFactory;
import com.example.currencytest.app.service.impl.CurrencyRateServiceImpl;
import com.example.currencytest.domain.CurrencyRate;
import com.example.currencytest.domain.enumration.CharCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CurrencyRateServiceImplTest {

    @Mock
    private List<CurrencyRate> rateList;

    @InjectMocks
    private CurrencyRateServiceImpl currencyRateService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSaveCurrencyRateFromCentralBank() {
        List<CurrencyRate> currencyRates = List.of(CurrencyRateFactory.FIRST_CURRENCY_RATE,
                CurrencyRateFactory.SECOND_CURRENCY_RATE);

        currencyRateService.saveCurrencyRateFromCentralBank(currencyRates);

        verify(rateList).clear();
        verify(rateList).addAll(currencyRates);
    }

}
