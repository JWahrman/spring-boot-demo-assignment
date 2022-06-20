package com.example.demo;

import com.example.demo.model.Currency;
import com.example.demo.model.CurrencyExchangeRates;
import com.example.demo.model.ExchangeAmountResponse;
import com.example.demo.service.DemoService;
import com.example.demo.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeAmountUnitTest {

    @Mock
    ExchangeRateService exchangeRateService;

    @InjectMocks
    DemoService demoService;

    @Test
    public void testExchangeAmount() throws IOException {
        when(exchangeRateService.getExchangeRates()).thenReturn(
                List.of(new CurrencyExchangeRates(Currency.EUR.getValue(), Map.ofEntries(
                        entry(Currency.EUR.getValue(), 1),
                        entry(Currency.SEK.getValue(), 10.663496),
                        entry(Currency.USD.getValue(), 1.052698)))));

        ExchangeAmountResponse sekExchangeAmount = demoService.exchangeAmount(Currency.EUR, Currency.SEK, BigDecimal.valueOf(5));
        assertEquals(Currency.EUR, sekExchangeAmount.getFrom());
        assertEquals(Currency.SEK, sekExchangeAmount.getTo());
        assertEquals(BigDecimal.valueOf(53.31748).setScale(3, RoundingMode.HALF_UP), sekExchangeAmount.getToAmount().setScale(3, RoundingMode.HALF_UP));

        ExchangeAmountResponse usdExchangeAmount = demoService.exchangeAmount(Currency.EUR, Currency.USD, BigDecimal.valueOf(1.55));
        assertEquals(Currency.EUR, usdExchangeAmount.getFrom());
        assertEquals(Currency.USD, usdExchangeAmount.getTo());
        assertEquals(BigDecimal.valueOf(1.6316819).setScale(3, RoundingMode.HALF_UP), usdExchangeAmount.getToAmount().setScale(3, RoundingMode.HALF_UP));

    }

}
