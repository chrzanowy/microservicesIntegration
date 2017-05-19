package com.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Kuba on 2017-05-19.
 */
@AllArgsConstructor
@Getter
public class RsoDto implements Serializable{
    private final String location;
    private final boolean isRiverState;
    private final String startDate;
    private final String endDate;
    private final String state;
    private final String riverName;
    private final String waterLevel;
    private final String waterLevelAlarm;
    private final String waterWarningLevelAlarm;
    private final String content;
}
