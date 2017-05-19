package com.integration.dto;

import lombok.Getter;

/**
 * Created by Kuba on 2017-05-19.
 */

@Getter
public class RsoRiverDto extends RsoDto {
    private final String riverName;
    private final String waterLevel;
    private final String waterLevelAlarm;
    private final String waterWarningLevelAlarm;

    public RsoRiverDto(String location, boolean isRiverState, String startDate, String endDate, String state,
                       String riverName, String waterLevel, String waterLevelAlarm, String waterWarningLevelAlarm) {
        super(location, isRiverState, startDate, endDate, state);
        this.riverName = riverName;
        this.waterLevel = waterLevel;
        this.waterLevelAlarm = waterLevelAlarm;
        this.waterWarningLevelAlarm = waterWarningLevelAlarm;
    }
}
