package com.integration.exception;

/**
 * Created by Kuba on 2017-05-12.
 */
public class UserAlreadySubscribedException extends Throwable {
    public UserAlreadySubscribedException(String email) {
        super(String.format("User with mail %s already has a subscription", email));
    }
}
