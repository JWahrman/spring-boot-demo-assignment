package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExchangeRates {

    private final Currency base;
    private final Map<Currency, BigDecimal> rates;

    @JsonCreator
    public CurrencyExchangeRates(@JsonProperty("base") String base, @JsonProperty("rates") Map<String, Object> rates) {
        this.base = Currency.findByName(base);
        this.rates = this.mapRates(rates);
    }

    public Currency getBase() {
        return base;
    }

    public Map<Currency, BigDecimal> getRates() {
        return rates;
    }

    private Map<Currency, BigDecimal> mapRates(Map<String, Object> rates) {

        Map<Currency, BigDecimal> mappedRates = new HashMap<>();

        rates.forEach((key, value) -> {
            mappedRates.put(Currency.findByName(key), BigDecimal.valueOf(Double.parseDouble(value.toString())));
        });

        return mappedRates;
    }
}
