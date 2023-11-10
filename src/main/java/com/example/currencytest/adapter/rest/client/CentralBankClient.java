package com.example.currencytest.adapter.rest.client;

import com.example.currencytest.adapter.facade.CurrencyRateFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Data
@RequiredArgsConstructor
public class CentralBankClient {
    @Value("${central.bank.url}")
    private String centralBankUrl;
    private final WebClient webClient;
    private final CurrencyRateFacade facade;

    @Scheduled(cron = "${timer.first}")
    @Scheduled(cron = "${timer.second}")
    public void getAndSaveCurrencyFromCbBank() {
        facade.saveCurrencyRateFromCB(webClient.mutate()
                .baseUrl(centralBankUrl).build()
                .get().retrieve()
                .bodyToMono(String.class)
                .block());
    }
}
