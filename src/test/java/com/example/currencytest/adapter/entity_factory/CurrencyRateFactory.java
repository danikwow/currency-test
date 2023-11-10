package com.example.currencytest.adapter.entity_factory;

import com.example.currencytest.domain.CurrencyRate;

public class CurrencyRateFactory {
    public static final CurrencyRate FIRST_CURRENCY_RATE = new CurrencyRate(1L, 2, "EUR", 1, "Euro", 90D);
    public static final CurrencyRate SECOND_CURRENCY_RATE = new CurrencyRate(2L, 3, "USD", 1, "Dollar",36D);
}
