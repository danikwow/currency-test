package com.example.currencytest.app.service;

import com.example.currencytest.domain.CurrencyRate;
import com.example.currencytest.domain.enumration.CharCode;

import java.util.List;
import java.util.Optional;

public interface CurrencyRateService {
    void saveCurrencyRateFromCentralBank(List<CurrencyRate> currencyRateList);
    Optional<CurrencyRate> getActualCurrencyRateByCode(CharCode code);

}
