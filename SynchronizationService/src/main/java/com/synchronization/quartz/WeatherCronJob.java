package com.synchronization.quartz;

/**
 * Created by Kuba on 2017-05-13.
 */

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeatherCronJob implements Job {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jmsTemplate.convertAndSend("WEATHER_SYNCHRONIZATION_QUEUE", context.getFireTime());
    }
}