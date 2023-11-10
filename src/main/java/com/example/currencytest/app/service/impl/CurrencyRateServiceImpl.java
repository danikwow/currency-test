package com.example.currencytest.app.service.impl;

import com.example.currencytest.app.service.CurrencyRateService;
import com.example.currencytest.domain.CurrencyRate;
import com.example.currencytest.domain.enumration.CharCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {
    private final List<CurrencyRate> rateList;

    public CurrencyRateServiceImpl(@Qualifier(value = "rateList") List<CurrencyRate> rateList) {
        this.rateList = rateList;
    }

    @Transactional
    @Override
    public void saveCurrencyRateFromCentralBank(List<CurrencyRate> currencyRateList) {
        rateList.clear();
        rateList.addAll(currencyRateList);
        log.info("Scheduled task result: " + currencyRateList.toString());
    }

    public Optional<CurrencyRate> getActualCurrencyRateByCode(CharCode code) {
        return rateList.stream()
                .filter(currencyRate -> currencyRate.getCharCode().equals(code.name()))
                .findFirst();
    }

}
