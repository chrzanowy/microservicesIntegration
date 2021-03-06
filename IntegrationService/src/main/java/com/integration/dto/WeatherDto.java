package com.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Kuba on 2017-05-19.
 */
@AllArgsConstructor
@Getter
@Builder
public class WeatherDto implements Serializable {
    private final Double temperature;
    private final Integer pressure;
    private final Integer humidity;
    private final Double temperatureMax;
    private final Double temperatureMin;
    private final Integer rain;
    private final Integer clouds;
    private final String date;
}