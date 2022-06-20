package com.example.demo.service;

import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyExchangeRates;
import com.example.demo.model.ExchangeAmountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;

@Service
public class DemoService {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public DemoService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public ExchangeAmountResponse exchangeAmount(Currency fromCurrency, Currency toCurrency, BigDecimal fromAmount) throws IOException {

        CurrencyExchangeRates exchangeRates = this.exchangeRateService.getExchangeRates()
                .stream()
                .filter(rate -> rate.getBase() == fromCurrency)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find exchange rates with base currency: "+fromCurrency));

        BigDecimal targetRate = exchangeRates.getRates().get(toCurrency);

        BigDecimal convertedValue = fromAmount.multiply(targetRate);

        return new ExchangeAmountResponse(fromCurrency, toCurrency, convertedValue, targetRate);
    }
}
