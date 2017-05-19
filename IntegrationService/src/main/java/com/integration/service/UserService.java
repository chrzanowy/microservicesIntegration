package com.integration.service;

import com.integration.dao.UserDao;
import com.integration.dto.subscription.SubscribeDto;
import com.integration.exception.UserAlreadySubscribedException;
import com.integration.exception.UserNotExistingException;
import com.integration.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kuba on 2017-05-12.
 */
@Service
public class UserService {

    private Map<String, User> userMap = new HashMap<>();

    @Autowired
    private UserDao userDao;

    public User subscribeUser(SubscribeDto subscribeDto) throws UserAlreadySubscribedException {
        User user = new User(subscribeDto.getEmail(), subscribeDto.getCity(),
                subscribeDto.getState());
        userDao.persist(user);
        return user;
    }

    public void unsubscribeUser(SubscribeDto subscribeDto) throws UserNotExistingException {
        userDao.deleteByEmail(subscribeDto.getEmail());
    }

    public List<String> getUserCityList(){
        return userDao.findAllCities();
    }

    public List<String> getUserStateList(){
        return userDao.findAllStates();
    }

    public List<User> getUserListByCity(String city){
        return userDao.findAllByCity(city);
    }

    public List<User> getUserListByState(String state){
        return userDao.findAllByState(state);
    }
}
