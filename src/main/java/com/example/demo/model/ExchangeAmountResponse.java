package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ExchangeAmountResponse {

    private final Currency from;
    private final Currency to;
    private final BigDecimal toAmount;
    private final BigDecimal exchangeRate;

    public ExchangeAmountResponse(Currency from, Currency to, BigDecimal toAmount, BigDecimal exchangeRate) {
        this.from = from;
        this.to = to;
        this.toAmount = toAmount;
        this.exchangeRate = exchangeRate;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    @JsonProperty("to_amount")
    public BigDecimal getToAmount() {
        return toAmount;
    }

    @JsonProperty("exchange_rate")
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}

