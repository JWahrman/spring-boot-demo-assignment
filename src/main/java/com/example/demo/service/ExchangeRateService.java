package com.example.demo.service;

import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyExchangeRates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateService.class);

    private final static String JSON_FILE_NAME = "exchange_rates.json";


    @Value("${exchange.rate.apikey}")
    private String exchangeRatesApiKey;

    public List<CurrencyExchangeRates> getExchangeRates() throws IOException {

        File jsonFile = new File(JSON_FILE_NAME);
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(jsonFile, new TypeReference<>() {});
    }

    public void fetchAndSaveExchangeRates() {

        LOG.info("Started to fetch exchange rates...");

        WebClient client = WebClient.create();
        ObjectMapper objectMapper = new ObjectMapper();

        String currencies = Arrays.stream(Currency.values())
                .map(Currency::getValue)
                .collect(Collectors.joining(","));

        List<CurrencyExchangeRates> rates = Arrays.stream(Currency.values())
                .map(currency -> {
                    String response = client.get()
                            .uri("https://api.apilayer.com", uriBuilder -> uriBuilder
                                    .path("/exchangerates_data/latest")
                                    .queryParam("base", currency.getValue())
                                    .queryParam("symbols", currencies)
                                    .build())
                            .header("apikey", exchangeRatesApiKey)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();
                    try {
                        return objectMapper.readValue(response, CurrencyExchangeRates.class);
                    } catch (JsonProcessingException e) {
                        LOG.error(e.getMessage());
                    }
                    return null;
                }).collect(Collectors.toList());

        try {
            File jsonFile = new File(JSON_FILE_NAME);
            objectMapper.writeValue(jsonFile, rates);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        LOG.info("Exchange rates fetched");
    }
}
