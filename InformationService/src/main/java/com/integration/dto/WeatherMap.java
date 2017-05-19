package com.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Kuba on 2017-05-19.
 */
@AllArgsConstructor
@Getter
@Setter
public class WeatherMap implements Serializable {
    private final Map<String, List<WeatherDto>> weatherMap;
}
