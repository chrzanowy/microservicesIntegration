package com.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, List<String>> rsoFetchMap = new HashMap<>();
        List<String> userStateList = userService.getUserStateList();
        userStateList.forEach(s -> rsoFetchMap.put(s, userService.getUserListByState(s)));
        jmsTemplate.convertAndSend("RSO_REQUEST_QUEUE", rsoFetchMap);
    }

    @JmsListener(destination = "WEATHER_SYNCHRONIZATION_QUEUE")
    public void fetchWeather(Date fireTime) {
        Map<String, List<String>> weatherFetchMap = new HashMap<>();
        List<String> userStateList = userService.getUserCityList();
        userStateList.forEach(s -> weatherFetchMap.put(s, userService.getUserListByCity(s)));
        jmsTemplate.convertAndSend("WEATHER_REQUEST_QUEUE", weatherFetchMap);
    }

}
