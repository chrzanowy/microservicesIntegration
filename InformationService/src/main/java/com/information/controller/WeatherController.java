package com.information.controller;

import com.information.dto.weather.WeatherDto;
import com.information.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jake on 22.04.2017.
 */
@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public WeatherDto getLocalization(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude) {
        return weatherService.getLocalizationForLatitudeAndLongitude(latitude,longitude);
    }
}
