package com.integration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.dto.MailDto;
import com.integration.dto.MailType;
import com.integration.dto.RsoMap;
import com.integration.dto.WeatherMap;
import com.integration.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kuba on 2017-05-18.
 */
@Service
public class IntegrationService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private UserService userService;

    private ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "RSO_SYNCHRONIZATION_QUEUE")
    public void fetchLatestRso(Date fireTime) {
        List<String> userStateList = userService.getUserStateList();
        if(!userStateList.isEmpty()){
            jmsTemplate.convertAndSend("RSO_REQUEST_QUEUE", userStateList);
        }
    }

    @JmsListener(destination = "RSO_RESPONSE_QUEUE")
    public void processRsoFetchResponse(String rsoMap) throws IOException {
        RsoMap translatedMap = mapper.readValue(rsoMap, RsoMap.class);
        translatedMap.getRsoMap().keySet().forEach(state -> {
            List<String> usersEmailsWithCity = userService.getUserListByState(state)
                    .stream()
                    .map(User::getEmail)
                    .collect(Collectors.toList());
            try {
                sendMail(usersEmailsWithCity, mapper.writeValueAsString(translatedMap.getRsoMap().get(state)),
                        MailType.RSO.getType());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    @JmsListener(destination = "WEATHER_SYNCHRONIZATION_QUEUE")
    public void fetchWeather(Date fireTime) {
        List<String> userCityList = userService.getUserCityList();
        if(!userCityList.isEmpty()){
            jmsTemplate.convertAndSend("WEATHER_REQUEST_QUEUE", userCityList);
        }
    }

    @JmsListener(destination = "WEATHER_RESPONSE_QUEUE")
    public void processWeatherFetchResponse(String weatherMap) throws IOException {
        WeatherMap translatedMap = mapper.readValue(weatherMap, WeatherMap.class);
        translatedMap.getWeatherMap().keySet().forEach(city -> {
            List<String> usersEmailsWithCity = userService.getUserListByCity(city)
                    .stream()
                    .map(User::getEmail)
                    .collect(Collectors.toList());
            try {
                sendMail(usersEmailsWithCity, mapper.writeValueAsString(translatedMap.getWeatherMap().get(city)),
                        MailType.WEATHER.getType());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendMail(MailDto mailDto) {
        jmsTemplate.convertAndSend("MAIL_QUEUE", mailDto);
    }

    public void sendMail(String email, String content, String type) {
        sendMail(Collections.singletonList(email), content, type);
    }

    public void sendMail(List<String> emailList, String content, String type) {
        sendMail(new MailDto(emailList, content, type));
    }

}
