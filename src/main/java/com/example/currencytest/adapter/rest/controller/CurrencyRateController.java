package com.example.currencytest.adapter.rest.controller;

import com.example.currencytest.adapter.facade.CurrencyRateFacade;
import com.example.currencytest.adapter.rest.client.CentralBankClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Currency Rate Controller", description = "Controller to work with currency rate")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${currency.rate.url}")
public class CurrencyRateController {
    private final CentralBankClient client;

    @Operation(
            summary = "Getting currency exchanges from Liberty Bank",
            description = "The request triggers the mechanism for updating exchange rates.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful update",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content),
            })
    @GetMapping
    public ResponseEntity updateCurrencyRate() {
        client.getAndSaveCurrencyFromCbBank();
        return ResponseEntity.ok().build();
    }
}
