package com.integration.service;

import com.integration.dto.MailDto;
import com.integration.dto.RsoDto;
import com.integration.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Kuba on 2017-05-18.
 */
@Service
public class IntegrationService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private UserService userService;

    @JmsListener(destination = "RSO_SYNCHRONIZATION_QUEUE")
    public void fetchLatestRso(Date fireTime) {
        List<String> userStateList = userService.getUserStateList();
        jmsTemplate.convertAndSend("RSO_REQUEST_QUEUE", userStateList);
    }

    @JmsListener(destination = "RSO_RESPONSE_QUEUE")
    public void processRsoFetchResponse(Map <String, RsoDto> rsoMap) {
        sendMail(Collections.emptyList(), "cont");
    }

    @JmsListener(destination = "WEATHER_SYNCHRONIZATION_QUEUE")
    public void fetchWeather(Date fireTime) {
        List<String> userStateList = userService.getUserStateList();
        jmsTemplate.convertAndSend("WEATHER_REQUEST_QUEUE", userStateList);
    }

    @JmsListener(destination = "WEATHER_RESPONSE_QUEUE")
    public void processWeatherFetchResponse(Map <String, WeatherDto> weatherMap) {
        sendMail(Collections.emptyList(), "cont");
    }

    private void sendMail(MailDto mailDto){
        jmsTemplate.convertAndSend("MAIL_QUEUE", mailDto);
    }

    public void sendMail(String email, String content){
        sendMail(new MailDto(Collections.singletonList(email), content));
    }

    public void sendMail(List<String> emailList, String content){
        sendMail(new MailDto(emailList, content));
    }

}
