package com.integration.dao.impl;


import com.integration.dao.UserDao;
import com.integration.exception.UserAlreadySubscribedException;
import com.integration.exception.UserNotExistingException;
import com.integration.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    private static final String STATE_KEY = "state";
    private static final String CITY_KEY = "city";
    private static final String EMAIL_KEY = "email";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persist(User user) throws UserAlreadySubscribedException {
        Optional<User> byEmail = findByEmail(user.getEmail());
        if (!byEmail.isPresent()) {
            entityManager.persist(user);
            log.info("User %s saved in db", user.getEmail());
        } else {
            throw new UserAlreadySubscribedException(user.getEmail());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> e = cq.from(User.class);
        cq.where(cb.equal(e.get(EMAIL_KEY), email));
        Query query = entityManager.createQuery(cq);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List findAllByState(String state) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> e = cq.from(User.class);
        cq.where(cb.equal(e.get(STATE_KEY), state));
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List findAllStates() {
        return findAllByKey(STATE_KEY);
    }

    @Override
    public List findAllCities() {
        return findAllByKey(CITY_KEY);
    }

    private List findAllByKey(String key) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> e = cq.from(User.class);
        cq.select(e.get(key)).distinct(true);
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List findAllByCity(String city) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> e = cq.from(User.class);
        cq.where(cb.equal(e.get(CITY_KEY), city));
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void deleteByEmail(String email) throws UserNotExistingException {
        Optional<User> byEmail = findByEmail(email);
        if (byEmail.isPresent()) {
            entityManager.remove(byEmail.get());
            log.info("User %s deleted from db", email);
        } else {
            throw new UserNotExistingException(email);
        }
    }
}
