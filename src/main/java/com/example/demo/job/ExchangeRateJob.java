package com.example.demo.job;

import com.example.demo.service.ExchangeRateService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ExchangeRateJob implements Job {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Override
    public void execute(JobExecutionContext context) {
        this.exchangeRateService.fetchAndSaveExchangeRates();
    }
}
