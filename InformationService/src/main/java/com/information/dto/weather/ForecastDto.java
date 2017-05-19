package com.information.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Jake on 14.05.2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ForecastDto {
    @JsonProperty("list")
    private List<WeatherDto> weatherDtoList;
    @JsonProperty("city")
    private WeatherCityDto weatherCityDto;
}
