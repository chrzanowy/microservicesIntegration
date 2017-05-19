package com.information.service;

import com.information.dto.weather.ForecastDto;
import com.information.dto.weather.ExtendedWeatherDto;
import com.integration.dto.WeatherDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jake on 22.04.2017.
 */
@Service
public class WeatherService {


    private static final String WEATHER_API_KEY = "ae07914d3182f59a5e597279c1d4a85c";

    private final RestTemplate restTemplate = new RestTemplate();

    public ExtendedWeatherDto getLocalizationForLatitudeAndLongitude(String latitude, String longitude) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("latitude", latitude);
        parameterMap.put("longitude", longitude);
        parameterMap.put("key", WEATHER_API_KEY);
        ExtendedWeatherDto weatherResponse = restTemplate
                .getForObject("http://api.openweathermap.org/data/2.5/weather?lat={latitude}&lon={longitude}&appid={key}&units=metric",
                        ExtendedWeatherDto.class,
                        parameterMap);
        weatherResponse.getClass();
        return weatherResponse;
    }

    public Map<String, ForecastDto> fetchWeatherForCities(List<String> allCities) {
        Map<String, ForecastDto> forecastMap = new HashMap<>();
        allCities.forEach((city) -> {
            Map<String, String> parameterMap = new HashMap<>();
            parameterMap.put("key", WEATHER_API_KEY);
            parameterMap.put("city", city);
            ForecastDto weatherResponse = restTemplate
                    .getForObject("http://api.openweathermap.org/data/2.5/forecast?q={city}&appid={key}&units=metric",
                            ForecastDto.class,
                            parameterMap);
            forecastMap.put(city, weatherResponse);

        });
        return forecastMap;
    }

    public WeatherDto translateForecastToWeather(ForecastDto forecastDto) {
        return null;
    }
}
