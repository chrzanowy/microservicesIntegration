package com.information.service;

import com.information.dto.rso.News;
import com.information.dto.weather.ForecastDto;
import com.integration.dto.RsoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
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
    private JmsTemplate jmsTemplate;

    @Autowired
    private RsoService rsoService;

    @Autowired
    private WeatherService weatherService;

    @JmsListener(destination = "WEATHER_REQUEST_QUEUE")
    public void fetchWeather(List<String> weatherFetchMap) {
        Map<String, ForecastDto> stringForecastDtoMap = weatherService.fetchWeatherForCities(weatherFetchMap);
        jmsTemplate.convertAndSend("WEATHER_RESPONSE_QUEUE",  Collections.emptyMap());
    }

    @JmsListener(destination = "RSO_REQUEST_QUEUE")
    public void fetchRso(List<String> rsoFetchMap) {
        List<RsoDto> latestNewsForStates = rsoService.getLatestNewsForStates(rsoFetchMap, 0L);
        jmsTemplate.convertAndSend("RSO_RESPONSE_QUEUE", Collections.emptyMap());
    }
}
