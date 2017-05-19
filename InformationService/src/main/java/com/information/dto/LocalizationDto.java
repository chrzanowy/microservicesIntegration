package com.information.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Created by Kuba on 2017-03-22.
 */
@AllArgsConstructor
@JsonPropertyOrder({
        "results",
        "status"
})
public class LocalizationDto {
    @JsonProperty("results")
    private List<Result> results = null;
    @JsonProperty("status")
    private String status;

    public List<Result> getResults() {
        return this.results;
    }

    public String getStatus() {
        return this.status;
    }
}
