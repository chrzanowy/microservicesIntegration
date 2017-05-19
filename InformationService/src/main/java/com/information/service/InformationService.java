package com.information.service;

import com.integration.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Kuba on 2017-05-18.
 */
@Service
public class InformationService {

    @Autowired
    private MailClient mailClient;

    @JmsListener(destination = "WEATHER_REQUEST_QUEUE")
    public void fetchWeather(Map<String, List<String>> weatherFetchMap) {
        System.out.println(weatherFetchMap);
        mailClient.sendMail(new MailDto(Collections.singletonList("weatger"), "weatger"));
    }

    @JmsListener(destination = "RSO_REQUEST_QUEUE")
    public void fetchRso(Map<String, List<String>> rsoFetchMap) {
        System.out.println(rsoFetchMap);
        mailClient.sendMail(new MailDto(Collections.singletonList("rso"), "rso"));
    }
}
