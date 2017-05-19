package com.information.service;

import com.information.dto.LocalizationDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuba on 2017-03-22.
 */
@Service
public class LocalizationService {

    private static final String GOOGLE_API_KEY = "AIzaSyBLYWgyATHaLTWpUzZdPKJqVBEYCxZabJU";

    private final RestTemplate restTemplate = new RestTemplate();

    public LocalizationDto getLocalizationForLatitudeAndLongitude(String latitude, String longitude) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("latlng", latitude + "," + longitude);
        parameterMap.put("key", GOOGLE_API_KEY);
        LocalizationDto forObject = restTemplate
                .getForObject("https://maps.googleapis.com/maps/api/geocode/json?latlng={latlng}&key={key}",
                        LocalizationDto.class,
                        parameterMap);
        forObject.getClass();
        return forObject;
    }

}
