package com.example.currencytest.adapter.facade;

import com.example.currencytest.adapter.dto.request.ValCurs;
import com.example.currencytest.adapter.mapper.ValuteMapper;
import com.example.currencytest.app.Exception.ProblemWithParsingXmlException;
import com.example.currencytest.app.service.CurrencyRateService;
import com.example.currencytest.domain.CurrencyRate;
import com.example.currencytest.domain.enumration.CharCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CurrencyRateFacade {
    private final XmlMapper xmlMapper;
    private final ValuteMapper valuteMapper;

    private final CurrencyRateService service;

    public void saveCurrencyRateFromCB(String currencyFromCbBank) {
        try {
            List<CurrencyRate> collect = xmlMapper.readValue(currencyFromCbBank, ValCurs.class)
                    .getValutes().stream()
                    .map(valuteMapper::toCurrencyRate)
                    .filter(currencyRate -> currencyRate.getCharCode().equals(CharCode.EUR.name())
                            || currencyRate.getCharCode().equals(CharCode.USD.name()))
                    .toList();

            service.saveCurrencyRateFromCentralBank(collect);

        } catch (JsonProcessingException e) {
            throw new ProblemWithParsingXmlException(e.getMessage());
        }
    }

}
