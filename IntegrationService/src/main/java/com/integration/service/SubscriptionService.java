package com.integration.service;

import com.integration.dto.MailType;
import com.integration.dto.subscription.SubscribeDto;
import com.integration.exception.UserAlreadySubscribedException;
import com.integration.exception.UserNotExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jake on 23.04.2017.
 */
@Service
public class SubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    private IntegrationService integrationService;

    @Transactional
    @javax.transaction.Transactional
    public void subscribe(SubscribeDto subscribeDto) throws UserAlreadySubscribedException {
        userService.subscribeUser(subscribeDto);
        integrationService.sendMail(subscribeDto.getEmail(), "Welcome in Weather & Rso information service",
                MailType.SUBSCRIBE.getType());
    }

    @Transactional
    @javax.transaction.Transactional
    public void unsubscribe(SubscribeDto subscribeDto) throws UserNotExistingException {
        userService.unsubscribeUser(subscribeDto);
        integrationService.sendMail(subscribeDto.getEmail(), "You have been unsubscribed",
                MailType.UNSUBSCRIBE.getType());
    }
}
