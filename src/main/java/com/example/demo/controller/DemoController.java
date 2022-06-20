package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
public class DemoController {

    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @PostMapping("/validate_ssn")
    public SsnValidationResponse getSsn(@RequestParam(value = "ssn") String ssn, @RequestParam("country_code") CountryCode countryCode) {
        return new SsnValidationResponse(Ssn.isValid(ssn));
    }

    @GetMapping("/exchange_amount")
    public ExchangeAmountResponse exchangeAmount(
            @RequestParam("from") Currency fromCurrency,
            @RequestParam("to") Currency toCurrency,
            @RequestParam("from_amount") BigDecimal fromAmount) throws IOException {

        return this.demoService.exchangeAmount(fromCurrency, toCurrency, fromAmount);
    }
}
