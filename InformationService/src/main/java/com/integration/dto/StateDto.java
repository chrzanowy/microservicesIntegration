package com.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Kuba on 2017-05-20.
 */
@AllArgsConstructor
@Getter
public class StateDto {
    private String state;
    private Long latestId;
}
