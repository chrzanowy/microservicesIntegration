package com.integration.dto;

import lombok.Getter;

/**
 * Created by Kuba on 2017-05-20.
 */
@Getter
public enum MailType {
    SUBSCRIBE("zapisz"),
    UNSUBSCRIBE("wypisz"),
    RSO("rso"),
    WEATHER("pogoda");

    private String type;

    MailType(String type) {
        this.type = type;
    }
}
