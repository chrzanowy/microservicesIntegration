package com.information.service;

import com.integration.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Kuba on 2017-05-18.
 */
@Service
public class MailClient {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMail(MailDto mailDto){
        jmsTemplate.convertAndSend("MAIL_QUEUE", mailDto);
    }

}
