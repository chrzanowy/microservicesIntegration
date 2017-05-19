package com.integration.service;

import com.integration.dto.MailDto;
import com.integration.dto.subscription.SubscribeDto;
import com.integration.exception.UserAlreadySubscribedException;
import com.integration.exception.UserNotExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Created by Jake on 23.04.2017.
 */
@Service
public class SubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void subscribe(SubscribeDto subscribeDto) throws UserAlreadySubscribedException {
        jmsTemplate.convertAndSend("MAIL_QUEUE", new MailDto(Collections.singletonList("rec"), "cont"));
       //userService.subscribeUser(subscribeDto);
        //mailSenderService.sendMail(subscribeDto.getEmail(), "Zostales zasubskrybowany do serwisu!");
    }


    public void unsubscribe(SubscribeDto subscribeDto) throws UserNotExistingException {
        //userService.unsubscribeUser(subscribeDto);
       // mailSenderService.sendMail(subscribeDto.getEmail(), "Zostales usuniety z subskrybcji!");
    }
}
