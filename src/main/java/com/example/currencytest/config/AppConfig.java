package com.example.currencytest.config;

import com.example.currencytest.domain.CurrencyRate;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@EnableWebMvc
@EnableScheduling
public class AppConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public XmlMapper mapper() {
        return new XmlMapper();
    }

    @Bean(name = "rateList")
    public List<CurrencyRate> rateList() {
        return new CopyOnWriteArrayList<>();
    }
}
