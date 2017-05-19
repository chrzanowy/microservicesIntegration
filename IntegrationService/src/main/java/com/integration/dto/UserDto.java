package com.integration.dto;

import lombok.Getter;

/**
 * Created by Kuba on 2017-05-18.
 */
@Getter
public class UserDto {
    private final String email;

    public UserDto(String email) {
        this.email = email;
    }
}
