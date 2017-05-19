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

    List<User> findAllByState(String state);

    List<String> findAllStates();

    List<String> findAllCities();

    List<User> findAllByCity(String city);

    Optional<User> findByEmail(String email);
}
