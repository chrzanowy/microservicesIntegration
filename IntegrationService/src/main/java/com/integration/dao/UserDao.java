package com.integration.dao;



import com.integration.exception.UserAlreadySubscribedException;
import com.integration.exception.UserNotExistingException;
import com.integration.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Lovababu on 4/26/2015.
 */
public interface UserDao {

    void persist(User user) throws UserAlreadySubscribedException;

    void deleteByEmail(String email) throws UserNotExistingException;

    List findAllByState(String state);

    List findAllStates();

    List findAllCities();

    List findAllByCity(String city);

    Optional<User> findByEmail(String email);
}
