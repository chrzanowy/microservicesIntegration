package com.information.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Created by Kuba on 2017-05-12.
 */
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    @JsonProperty("main")
    public Main main;
    @JsonProperty("wind")
    public Wind wind;
    @JsonProperty("clouds")
    public Clouds clouds;
    @JsonProperty("rain")
    public Rain rain;
    @JsonProperty("sys")
    public Sys sys;
    @JsonProperty("dt_txt")
    public String forecastDate;

    @JsonPropertyOrder({
            "3h"
    })
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rain {

        @JsonProperty("3h")
        public Integer _3h;
    }


    @JsonPropertyOrder({
            "all"
    })
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Clouds {

        @JsonProperty("all")
        public Integer all;
    }


    @JsonPropertyOrder({
            "speed",
            "deg"
    })
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Wind {

        @JsonProperty("speed")
        public Double speed;
        @JsonProperty("deg")
        public Integer deg;
    }

    @JsonPropertyOrder({
            "type",
            "id",
            "message",
            "country",
            "sunrise",
            "sunset"
    })
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sys {
        @JsonProperty("type")
        public Integer type;
        @JsonProperty("id")
        public Integer id;
        @JsonProperty("message")
        public Double message;
        @JsonProperty("country")
        public String country;
        @JsonProperty("sunrise")
        public Integer sunrise;
        @JsonProperty("sunset")
        public Integer sunset;
    }

    @JsonPropertyOrder({
            "temp",
            "pressure",
            "humidity",
            "temp_min",
            "temp_max"
    })
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Main {

        @JsonProperty("temp")
        public Double temp;
        @JsonProperty("pressure")
        public Integer pressure;
        @JsonProperty("humidity")
        public Integer humidity;
        @JsonProperty("temp_min")
        public Double tempMin;
        @JsonProperty("temp_max")
        public Double tempMax;
    }
}

