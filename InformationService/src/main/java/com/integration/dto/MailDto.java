package com.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kuba on 2017-05-18.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MailDto implements Serializable {

    private List<String> emailList;
    private String content;
}
