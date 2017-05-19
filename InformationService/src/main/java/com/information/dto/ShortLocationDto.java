package com.information.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Created by Kuba on 2017-03-22.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortLocationDto {
    @JsonProperty
    private final String street;
    @JsonProperty
    private final String city;
    @JsonProperty
    private final String number;

    public ShortLocationDto(LocalizationDto localizationDto) {
        Result localizationResult = localizationDto.getResults().stream().findFirst().orElse(null);
        if (localizationResult != null) {
            this.city = getParameterFromResponse(localizationResult, "locality");
            this.number = getParameterFromResponse(localizationResult, "street_number");
            this.street = getParameterFromResponse(localizationResult, "route");
        } else {
            this.city = this.number = this.street = "";
        }
    }

    private String getParameterFromResponse(Result localizationResult, String parameter) {
        return localizationResult.getAddressComponents().stream().filter(addressComponent -> addressComponent.getTypes()
                .contains(parameter)).findFirst().map(addressComponent -> addressComponent.getLongName()).orElse("");
    }
}
