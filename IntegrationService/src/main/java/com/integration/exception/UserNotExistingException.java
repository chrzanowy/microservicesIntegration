package com.integration.exception;

/**
 * Created by Kuba on 2017-05-12.
 */
public class UserNotExistingException extends Exception {
    public UserNotExistingException(String email) {
        super(String.format("User with mail %s is not existing", email));
    }
}
