package com.example.currencytest.adapter.rest.client;

import com.example.currencytest.adapter.facade.CurrencyRateFacade;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    @Scheduled(cron = "${scheduled.timer}")
    @PostConstruct
    public void getAndSaveCurrencyFromCbBank() {
        facade.saveCurrencyRateFromCB(webClient.mutate()
                .baseUrl(centralBankUrl).build()
                .get().retrieve()
                .bodyToMono(String.class)
                .block());
    }
}
