package com.mailservice.service;

import com.integration.dto.MailDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by Kuba on 2017-05-18.
 */
@Service
public class MailService {

    private static final String MAIL_API_KEY = "$bE6Ft}3*2Eu<s>H]dh,ue/vB%d5:d@!NFoo{:QE";
    private static final String MAIL_BOX_URL = "http://serwer1399643.home.pl/sendMail/";

    private RestTemplate restTemplate = new RestTemplate();

    @JmsListener(destination = "MAIL_QUEUE")
    public void sendMail(MailDto mailDto) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap();
//        parameterMap.put("apiKlucz", Collections.singletonList(MAIL_API_KEY));
//        parameterMap.put("odbiorca", mailDto.getEmailList());
//        parameterMap.put("tresc", Collections.singletonList(mailDto.getContent()));
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameterMap, headers);
        //ResponseEntity<String> response = restTemplate.postForEntity(MAIL_BOX_URL, request, String.class);
        System.out.println("sending mail to " + mailDto.getEmailList() + " with content " + mailDto.getContent());
    }

}
