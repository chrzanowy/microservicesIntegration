package com.information.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.dto.RsoDto;
import com.integration.dto.RsoMap;
import com.integration.dto.WeatherDto;
import com.integration.dto.WeatherMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Kuba on 2017-05-18.
 */
@Service
public class InformationService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private RsoService rsoService;

    @Autowired
    private WeatherService weatherService;

    private ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "WEATHER_REQUEST_QUEUE")
    public void fetchWeather(List<String> weatherFetchMap) throws JsonProcessingException {
        Map<String, List<WeatherDto>> stringForecastDtoMap = weatherService.fetchWeatherForCities(weatherFetchMap);
        jmsTemplate.convertAndSend("WEATHER_RESPONSE_QUEUE", mapper.writeValueAsString(new
                WeatherMap(stringForecastDtoMap)));
    }

    @JmsListener(destination = "RSO_REQUEST_QUEUE")
    public void fetchRso(List<String> rsoFetchMap) throws JsonProcessingException {
        Map<String, List<RsoDto>> latestNewsForStates = rsoService.getLatestNewsForStates(rsoFetchMap, 0L);
        jmsTemplate.convertAndSend("RSO_RESPONSE_QUEUE", mapper.writeValueAsString(new RsoMap(latestNewsForStates)));
    }
}
