package com.integration.dto;

import lombok.Getter;

/**
 * Created by Kuba on 2017-05-19.
 */
@Getter
public abstract class RsoDto {
    private final String location;
    private final boolean isRiverState;
    private final String startDate;
    private final String endDate;
    private final String state;

    protected RsoDto(String location, boolean isRiverState, String startDate, String endDate, String state) {
        this.location = location;
        this.isRiverState = isRiverState;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
    }
}
