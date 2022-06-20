package com.example.demo.configuration;

import com.example.demo.job.ExchangeRateJob;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;


@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class QuartzConfiguration {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext)
    {
        SchedulerJobFactory jobFactory = new SchedulerJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler schedulerFactoryBean(JobFactory jobFactory,
                                          Trigger trigger,
                                          JobDetail jobDetail) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        factory.setJobFactory(jobFactory);

        factory.setQuartzProperties(quartzProperties());
        factory.afterPropertiesSet();

        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(jobFactory);
        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
        return scheduler;
    }

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(ExchangeRateJob.class)
                .storeDurably()
                .withIdentity("Exchange_Rate_Job_Detail")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity("Exchange_Rate_Trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInHours(1))
                .build();
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}
