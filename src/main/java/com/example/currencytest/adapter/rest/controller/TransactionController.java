package com.example.currencytest.adapter.rest.controller;

import com.example.currencytest.adapter.dto.request.ReportRequestDto;
import com.example.currencytest.adapter.dto.response.TransactionDto;
import com.example.currencytest.adapter.facade.TransactionFacade;
import com.example.currencytest.domain.enumration.CharCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Tag(name = "TransactionController", description = "Controller to work with transaction")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"${transaction.url}"})
public class TransactionController {
    private final TransactionFacade facade;

    @Operation(
            summary = "Getting currency exchanges from Liberty Bank",
            description = "Request to create a new transaction")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Retrieving list of Transaction",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD_REQUEST",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content),
            })
    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(facade.createNewTransaction(transactionDto));
    }

    @Operation(
            description = "Request to receive a report on financial transactions for a period in a specified currency",
            summary = "Date format DD-Mon-YYY - example 09-11-2023" )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Retrieving list of Transaction",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content),
            })
    @GetMapping
    public ResponseEntity<List<TransactionDto>> getReportBetweenDateAboutTransaction(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                                     @RequestParam CharCode code) {
        return ResponseEntity.ok(facade.getReportBetweenDate(startDate, endDate, code));
    }

}
