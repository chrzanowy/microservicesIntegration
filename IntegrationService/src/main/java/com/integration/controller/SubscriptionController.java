package com.integration.controller;


import com.integration.dto.subscription.SubscribeDto;
import com.integration.exception.UserAlreadySubscribedException;
import com.integration.exception.UserNotExistingException;
import com.integration.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jake on 23.04.2017.
 */
@RestController
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@RequestBody SubscribeDto subscribeDto) throws UserAlreadySubscribedException {
        subscriptionService.subscribe(subscribeDto);
    }


    @RequestMapping(value = "/subscribe", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe(@RequestBody SubscribeDto subscribeDto) throws UserNotExistingException {
        subscriptionService.unsubscribe(subscribeDto);
    }
}
