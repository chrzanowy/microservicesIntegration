package com.integration.dto;

import lombok.Getter;

/**
 * Created by Kuba on 2017-05-19.
 */
@Getter
public class RsoAccidentDto extends RsoDto {
    private final String content;

    public RsoAccidentDto(String location, boolean isRiverState, String startDate, String endDate, String content,
                          String state) {
        super(location, isRiverState, startDate, endDate, state);
        this.content = content;
    }
}
