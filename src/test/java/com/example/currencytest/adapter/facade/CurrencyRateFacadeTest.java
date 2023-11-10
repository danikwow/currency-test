package com.example.currencytest.adapter.facade;

import com.example.currencytest.adapter.dto.request.ValCurs;
import com.example.currencytest.adapter.dto.request.Valute;
import com.example.currencytest.adapter.entity_factory.CurrencyRateFactory;
import com.example.currencytest.adapter.mapper.ValuteMapper;
import com.example.currencytest.app.Exception.ProblemWithParsingXmlException;
import com.example.currencytest.app.service.CurrencyRateService;
import com.example.currencytest.domain.CurrencyRate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class CurrencyRateFacadeTest {

    @Mock
    private XmlMapper xmlMapper;

    @Mock
    private ValuteMapper valuteMapper;

    @Mock
    private CurrencyRateService service;

    @InjectMocks
    private CurrencyRateFacade currencyRateFacade;

    private final String currencyFromCbBank = "<ValCurs Date=\"11.11.2023\" name=\"Foreign Currency Market\">" +
            "<script data-timezone-ext-el=\"\" src=\"chrome-extension://cejckpjfigehbeecbbfhangbknpidcnh/data/inject-content.js\"/>" +
            "<Valute ID=\"R01010\"><NumCode>036</NumCode><CharCode>AUD</CharCode><Nominal>1</Nominal><Name>Австралийский доллар</Name><Value>58,5552</Value><VunitRate>58,5552</VunitRate></Valute>" +
            "<Valute ID=\"R01020A\"><NumCode>944</NumCode><CharCode>AZN</CharCode><Nominal>1</Nominal><Name>Азербайджанский манат</Name><Value>54,1491</Value><VunitRate>54,1491</VunitRate></Valute>" +
            "</ValCurs>";

    @BeforeEach
    void init() {
        openMocks(this);
    }

    @Test
    void testSaveCurrencyRateFromCB_Success() throws JsonProcessingException {

        ValCurs valCurs = new ValCurs();
        List<Valute> valutes = List.of(new Valute("122", 2, "EUR", 1, "Euro", 90D, 90d),
                new Valute("222", 3, "USD", 1, "Dollar", 36D, 36d));
        valCurs.setValutes(valutes);

        when(xmlMapper.readValue(currencyFromCbBank, ValCurs.class)).thenReturn(valCurs);

        List<CurrencyRate> currencyRates = List.of(CurrencyRateFactory.FIRST_CURRENCY_RATE,
                CurrencyRateFactory.SECOND_CURRENCY_RATE);
        when(valuteMapper.toCurrencyRate(any(Valute.class))).thenReturn(currencyRates.get(0), currencyRates.get(1));

        currencyRateFacade.saveCurrencyRateFromCB(currencyFromCbBank);

        verify(service).saveCurrencyRateFromCentralBank(eq(currencyRates));
        verify(valuteMapper, times(2)).toCurrencyRate(any(Valute.class));
    }

    @Test
    void testSaveCurrencyRateFromCB_ParseError() throws JsonProcessingException {
        doThrow(JsonProcessingException.class).when(xmlMapper).readValue(currencyFromCbBank, ValCurs.class);

        assertThrows(ProblemWithParsingXmlException.class, () -> currencyRateFacade.saveCurrencyRateFromCB(currencyFromCbBank));
    }
}