package com.integration.dto.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Jake on 23.04.2017.
 */

@Getter
@NoArgsConstructor
public class SubscribeDto {
    @JsonProperty
    private String email;
    @JsonProperty
    private String city;
    @JsonProperty
    private String state;


    public SubscribeDto(String email, String city, String state) {
        this.email = email;
        this.city = city;
        this.state = state;
    }
}
