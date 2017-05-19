package com.information.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Created by Kuba on 2017-03-22.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "address_components",
        "formatted_address",
        "geometry",
        "place_id",
        "types"
})
@AllArgsConstructor
public class Result {

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents = null;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    public List<AddressComponent> getAddressComponents() {
        return this.addressComponents;
    }

    public String getFormattedAddress() {
        return this.formattedAddress;
    }
}
